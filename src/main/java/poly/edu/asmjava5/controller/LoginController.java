package poly.edu.asmjava5.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.asmjava5.auth.Role;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.model.AccountDto;
import poly.edu.asmjava5.model.LoginDto;
import poly.edu.asmjava5.service.AccountAuthenticationService;
import poly.edu.asmjava5.service.AccountService;
import poly.edu.asmjava5.service.EmailService;
import poly.edu.asmjava5.service.impl.MailServiceImpl;

@Controller
public class LoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @Autowired
    BCryptPasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    @Autowired
    AccountAuthenticationService accountAuthenticationService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("account", new LoginDto());
        return "site/login2";
    }

//    @PostMapping("/login")
//    public String login(
//            Model model,
//            @Valid @ModelAttribute("account") LoginDto dto,
//            BindingResult result
//    ) {
//        System.out.println("sdasdas");
//        if (result.hasErrors()) {
//            return "site/login2";
//        }
//        try {
//            Account user = accountService.findById(dto.getUsername()).orElse(null);
//            if (user == null) { //!user.getPassword().equals(dto.getPassword())
//                model.addAttribute("message", "Sai mật khẩu");
//                return "site/login2";
//            } else {
//                session.setAttribute("user", user);
////                String redirectUrl = determineRedirectUrl(user);
//                return "redirect:/home";
//            }
//        }catch (Exception e) {
//            model.addAttribute("message", "Không tìm thấy username");
//        }
//        return "site/login2";
//    }

//    private String determineRedirectUrl(Account user) {
//        if (user.isAdmin()) {
//            return "/admin/accounts";
//        } else {
//            return "/home";
//        }
//    }

    @GetMapping("/login/success")
    public String LoginOAuth2(OAuth2AuthenticationToken oauth, Model model) {
        //!Lưu vào database
         Account account = accountAuthenticationService.loginOAuthen2(oauth);
        session.setAttribute("username",account.getUsername());
        /*model.addAttribute("user", account);*/
        emailService.sendMail(account, "welcome"); // gửi mail nếu đăng nhập bằng oauth
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new AccountDto());
        return "site/register";
    }

    @PostMapping("/register")
    public String saveReg(
            Model model,
            @Valid @ModelAttribute("account") AccountDto dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "site/register";
        }
        Account account = new Account();
        dto.setPassword(pe.encode(dto.getPassword()));
        dto.setActive(Boolean.TRUE);
        dto.setRole(Role.USER);
        account.setRole(Role.USER);
        System.out.println(dto);
        BeanUtils.copyProperties(dto, account);
        System.out.println(account);
        emailService.sendMail(account, "welcome");
        account = accountService.save(account);
        model.addAttribute("message", "Tạo tài khoản thành công");
        return "site/login2";
    }
}
