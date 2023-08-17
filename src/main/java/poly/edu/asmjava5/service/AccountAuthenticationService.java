package poly.edu.asmjava5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import poly.edu.asmjava5.auth.Role;
import poly.edu.asmjava5.domain.Account;

import java.util.ArrayList;

@Configuration
public class AccountAuthenticationService implements UserDetailsService {

    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountService.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username is not found!"));
    }

    public Account loginOAuthen2(OAuth2AuthenticationToken oauth) {
        // lấy dữ liệu từ nền tảng mạng xã hội và google
        String username = oauth.getPrincipal().getAttribute("email");
        String fullname = oauth.getPrincipal().getAttribute("name");
        String email = oauth.getPrincipal().getAttribute("email");
        String phone = null;
        if (oauth.getPrincipal().getAttribute("phone") != null) {
            phone = oauth.getPrincipal().getAttribute("phone");
        } else {
            phone = "0123123123";
        }
        String password = Long.toHexString(System.currentTimeMillis()); // lấy thời gian của hệ thông làm password
        Account acc = checkAccountExists(username, fullname, email, password, phone);
        return  acc;
    }

    public Account checkAccountExists(String username, String fullname, String email, String password, String phone) {
        Account account = accountService.findByEmail(email);
        if (account != null) {
            System.out.println("Tài khoản gg của bạn là: " + account);
            return account;
        } else {
            account = new Account();
            account.setActive(Boolean.TRUE); // cho hn quyền hoạt động
            account.setUsername(username);
            account.setFullname(fullname);
            account.setPassword(password);
            account.setEmail(email);
            account.setPhone(phone);
            account.setRole(Role.USER); // luôn cho hn là user
            return accountService.save(account);
        }
    }
}
