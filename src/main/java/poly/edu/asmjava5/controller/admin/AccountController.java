package poly.edu.asmjava5.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.model.AccountDto;
import poly.edu.asmjava5.service.AccountService;
import poly.edu.asmjava5.service.EmailService;
import poly.edu.asmjava5.service.impl.MailServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/accounts")
    /*
        !: Trang thêm sản phẩm và danh sách nằm trên 1 trang
    */
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    BCryptPasswordEncoder pe;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDto());
        return "admin/accounts/addOrEdit";
    }

    @GetMapping("edit/{username}")
    public String edit(ModelMap model
            , RedirectAttributes redirectAttributes
            , @PathVariable("username") String username) {
        Optional<Account> opt = accountService.findById(username);
        AccountDto dto = new AccountDto();
        if (opt.isPresent()) {
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setPassword(pe.encode(dto.getPassword())); // mã hóa lại password
//            dto.setIdEdit(true); // cho phép sửa đổi thông tin
            model.addAttribute("account", dto);
            return "/admin/accounts/addOrEdit";
        }
        redirectAttributes.addFlashAttribute("message", "account is not existed!");

        return "redirect:/admin/accounts/add";
    }

    @GetMapping("delete/{username}")
    public String delete(@PathVariable("username") String username
            ,ModelMap model) {
        accountService.deleteById(username);
        model.addAttribute("message", "account is deleted!");
        return "forward:/admin/accounts";
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdate(ModelMap model
            , @Valid @ModelAttribute("account") AccountDto dto
            , BindingResult result
            , RedirectAttributes redirectAttributes) {

        // kiểu tra dữ liệu nếu ko nhập mà nhấn lưu
        if (result.hasErrors()) {
            return "/admin/accounts/addOrEdit";
        }
        Account entity = new Account();
        dto.setActive(Boolean.TRUE);
        dto.setPassword(pe.encode(dto.getPassword())); // mã hóa password
        BeanUtils.copyProperties(dto, entity);

        entity = accountService.save(entity);

        if (entity != null) {
            redirectAttributes.addFlashAttribute("message", "Category is saved!");
        }

        return "redirect:/admin/accounts";
    }

    // Ngầm định là khi chạy trang add thì  chạy luôn phần list(danh sách accounts) phía dưới luôn
    @GetMapping("")
    public String list(
            ModelMap model,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo,
            @RequestParam(name = "name", defaultValue = "") String name
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);

        model.addAttribute("name", name);
        Page<Account> page = accountService.findAllByUsernameLike("%"+name+"%",pageable);
        model.addAttribute("accounts", page);
        return "/admin/accounts/listAccount";
    }

    @GetMapping("search")
    public String search(
            ModelMap model,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);
        Page<Account> list = null;
        if (StringUtils.hasText(name)) {
            list = accountService.findAllByUsernameLike("%"+name+"%",pageable);
        }else {
            list = accountService.findAll(pageable);
        }
        model.addAttribute("name", name);
        model.addAttribute("accounts", list);
        return "admin/accounts/listAccount";
    }

}

