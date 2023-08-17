package poly.edu.asmjava5.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.domain.Order;
import poly.edu.asmjava5.domain.Product;
import poly.edu.asmjava5.model.AccountDto;
import poly.edu.asmjava5.model.CategoryDto;
import poly.edu.asmjava5.model.OrderDto;
import poly.edu.asmjava5.model.ProductDto;
import poly.edu.asmjava5.service.AccountService;
import poly.edu.asmjava5.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @ModelAttribute("account")
    public List<AccountDto> getCategories() {
        return accountService.findAll().stream().map(item -> {
            AccountDto dto = new AccountDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }

    @GetMapping("add")
    public String getOrder(Model model) {
        model.addAttribute("order", new OrderDto());
        return "/admin/orders/addOrEdit";
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdateOrder(
            Model model,
            @Valid @ModelAttribute("order") OrderDto dto,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        if (result.hasErrors()) {
            return "admin/orders/addOrEdit";
        }
        Order order = new Order();
        Account account = new Account();
        account.setUsername(dto.getUsername());
        BeanUtils.copyProperties(dto, order);
        order.setOrderDate(new Date());
        order.setUsername(account);
        order = orderService.save(order);

        if (order != null) {
            attributes.addFlashAttribute("message", "Order saved successfully!");
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("")
    public String listOrder(
            Model model,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo,
            @RequestParam(name = "username", required = false, defaultValue = "") String username
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0),5);
        model.addAttribute("username", username);
        Page<Order> page = orderService.findAll(pageable);
        model.addAttribute("orders", page);
        return "admin/orders/listOrder";
    }

    @GetMapping("search")
    public String search(
            ModelMap model,
            @RequestParam(name = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "pageNo", defaultValue = "0") Optional<Integer> pageNo
    ) {
        Pageable pageable = PageRequest.of(pageNo.orElse(0), 5);
        Page<Order> list = null;
        if (StringUtils.hasText(username)) {
            list = orderService.findAllByUsernameLike("%"+username+"%",pageable);
        }else {
            list = orderService.findAll(pageable);
        }
        model.addAttribute("username", username);
        model.addAttribute("orders", list);
        return "admin/orders/listOrder";
    }

    @GetMapping("edit/{orderId}")
    public String edit(ModelMap model
            , RedirectAttributes redirectAttributes
            , @PathVariable("orderId") Long orderId) {
        Optional<Order> opt = orderService.findById(orderId);
        OrderDto dto = new OrderDto();
        if (opt.isPresent()) {
            Order entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setUsername(entity.getUsername().getUsername());
//            dto.setIdEdit(true); // cho phép sửa đổi thông tin
            model.addAttribute("order", dto);
            return "/admin/orders/addOrEdit";
        }
        redirectAttributes.addFlashAttribute("message", "Order is not existed!");

        return "/admin/orders/addOrEdit";
    }

    @GetMapping("delete/{orderId}")
    public String delete(@PathVariable("orderId") Long orderId
            ,ModelMap model) {
        orderService.deleteById(orderId);
        model.addAttribute("message", "order is deleted!");
        return "forward:/admin/order";
    }
}
