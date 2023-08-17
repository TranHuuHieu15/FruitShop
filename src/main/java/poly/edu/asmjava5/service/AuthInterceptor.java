package poly.edu.asmjava5.service;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import poly.edu.asmjava5.domain.Account;

//@Service
//public class AuthInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    HttpSession session;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String uri = request.getRequestURI();
//        Account user = (Account) session.getAttribute("user");
//
//        if (user == null) {
//            // Chưa đăng nhập
//            response.sendRedirect("/login");
//            return false;
//        } else if (!user.isAdmin() && uri.startsWith("/admin/")){
//            // Không có quyền truy cập admin
//            response.sendRedirect("/404");
//            return false;
//        }
//
//        return true;
//    }
//}
