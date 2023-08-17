package poly.edu.asmjava5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import poly.edu.asmjava5.service.AccountAuthenticationService;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    AccountAuthenticationService authenticationService;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(getBCryptPasswordEncoder());
        dao.setUserDetailsService(authenticationService);
        dao.setHideUserNotFoundExceptions(false);
        return dao;
    }
}
