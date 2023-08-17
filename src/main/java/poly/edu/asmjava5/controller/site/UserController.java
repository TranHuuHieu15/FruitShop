package poly.edu.asmjava5.controller.site;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import poly.edu.asmjava5.auth.Role;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.domain.Product;
import poly.edu.asmjava5.model.AccountDto;
import poly.edu.asmjava5.service.AccountService;
import poly.edu.asmjava5.service.EmailService;
import poly.edu.asmjava5.service.ProductService;
import poly.edu.asmjava5.service.StorageService;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;

    @Autowired
    StorageService storageService;

    @Autowired
    AccountService accountService;

    @Autowired
    BCryptPasswordEncoder pe;

    @Autowired
    EmailService emailService;

    @GetMapping("/index")
    public String index() {
        return "site/index";
    }

    @GetMapping("/home")
    public String home() {
        return "user/home";
    }

    //Method get
    @GetMapping("/info")
    public String info(Model model) {
        // lay thong tin nguoi dung tư session
//
        String username = "";
        if(session.getAttribute("username") == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = authentication.getName();
        }else{
            username =(String) session.getAttribute("username");
        }
        System.out.println(username);
        Account account = accountService.findById(username).orElse(null);
        System.out.println("thông tin của account" + account);
        model.addAttribute("user", account);
        return "user/info";
    }

    //Method post
    @PostMapping("/info")
    public String updateInfo(
            Model model,
            @Valid @ModelAttribute("user") AccountDto accountDto,
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            return "user/info";
        }
        // Đảm bảo lấy thông tin người dùng hiện tại từ SecurityContextHolder
        // chừ tui lấy thêm cái session nữa hi
        String username = "";
        if(session.getAttribute("username") == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = authentication.getName();
        }else{
            username =(String) session.getAttribute("username");
        }
        // Lấy thông tin tài khoản từ cơ sở dữ liệu
        Account account = accountService.findById(username).orElse(null);
//        accountDto.setRole(Role.USER);
        //Sao chép thông tin từ AccountDto vào Account
        BeanUtils.copyProperties(accountDto, account);
        System.out.println(accountDto);

        //Đặt vai trò và mật khẩu
        account.setRole(Role.USER);
        account.setPassword(pe.encode(accountDto.getPassword()));

        // Cập nhật lại tài khoản
        account = accountService.save(account);

        // cập nhật đối tượng trong session
        session.setAttribute("user", account); // cập nhật lại user trong session

        //Thông báo thành công
        model.addAttribute("message","Cập nhật tài khoản thành công!");
        return "user/info";
    }

    @GetMapping("/about")
    public String about() {
        return "site/about";
    }

    @GetMapping("/news")
    public String news() {
        return "site/news";
    }

    @GetMapping("/contact")
    public String contact() {
        return "site/contact";
    }

    @GetMapping("/shop")
    public String shop(
            Model model
            ,@RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0) , 6);
        Page<Product> list = productService.findAll(pageable);
        model.addAttribute("products", list);
        return "site/shop";
    }

    @GetMapping("/single-product")
    public String singleProduct(Model model
            ,@RequestParam("id") Long product) {
       Optional<Product> product1 = productService.findById(product);

        model.addAttribute("product", product1);
        return "site/single-product";
    }

    @GetMapping("/404")
    public String nf404(Model model) {
        model.addAttribute("message","bạn không có quyền truy xuất trang này!");
        return "site/404";
    }

    @GetMapping("forgot")
    public String forgotPassword(Model model) {
        return "site/forgot";
    }

    @PostMapping("forgot")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        Account account = accountService.findByEmail(email);
        if (account != null) {
            int max = 9999;
            int min = 1000;
            String newPassword = String.valueOf((int) (Math.random() * ((max - min) + 1)) + min);
            account.setPassword(newPassword);
            accountService.save(account);
            emailService.sendMail(account, "forgot");
            model.addAttribute("message","Đã gửi mật khẩu về gmail của bạn vui lòng kiểm tra mail!");
            return "site/forgot";
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
