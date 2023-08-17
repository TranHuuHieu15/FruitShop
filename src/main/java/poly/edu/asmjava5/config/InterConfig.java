package poly.edu.asmjava5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import poly.edu.asmjava5.service.AuthInterceptor;

//@Configuration
//public class InterConfig implements WebMvcConfigurer {
//    @Autowired
//    AuthInterceptor authInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/admin/**","/info","/history-order")
//                .excludePathPatterns("/assets/**", "/dist/**","/plugins/**","/index","/login","/register"
//                        ,"/about","/news","/contact");
//    }
//
//}
