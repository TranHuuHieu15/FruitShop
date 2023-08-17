package poly.edu.asmjava5.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import poly.edu.asmjava5.auth.Role;


@Configuration
@EnableWebSecurity
public class SecurityLoginConfig {
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
                                .requestMatchers(
                                        "/assets/**", "/dist/**", "/plugins/**",
                                        "/index", "/login", "/register",
                                        "/about", "/news", "/contact",
                                        "/do-login","/forgot"
                                ).permitAll()
                                .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // Xử lý khi người dùng không có quyền truy cập
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            request.setCharacterEncoding("UTF-8");
                            response.setCharacterEncoding("UTF-8");
                            response.sendRedirect("/404");
                        })
                ).formLogin(
                        form -> {
                            form
                                    .loginPage("/login")
                                    .loginProcessingUrl("/login")
                                    .defaultSuccessUrl("/home", false)
                                    .failureUrl("/login") // nếu sai thì trả về trang login

                                    .permitAll();
                        }
                ).oauth2Login(
                        oauth2 -> oauth2
                                .loginPage("/login")
                                .defaultSuccessUrl("/login/success", true)
                                .failureUrl("/login/error=true")
                                .failureHandler((request, response, exception) -> {
                                    exception.printStackTrace();
                                })
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}
