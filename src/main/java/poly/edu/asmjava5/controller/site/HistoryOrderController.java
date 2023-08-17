package poly.edu.asmjava5.controller.site;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.domain.HistoryOrder;
import poly.edu.asmjava5.service.OrderService;

import java.util.List;

@Controller
public class HistoryOrderController {

    @Autowired
    HttpSession session;

    @Autowired
    OrderService orderService;

    @GetMapping("/history-order")
    public String getHistoryOrder(
        Model model,
        HistoryOrder historyOrder
    ) {
//        Account account = (Account) session.getAttribute("user");
//        String username = account.getUsername();
        String username = null;
        if (session.getAttribute("username")==null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = authentication.getName();
        } else {
            username = (String) session.getAttribute("username");
        }
        System.out.println(username);
        List<HistoryOrder> historyOrders = orderService.findOrderByUsername(username);
        System.out.println(historyOrders);
        model.addAttribute("historyOrders", historyOrders);
        return "site/historyOrder";
    }

/*    @PostMapping("/history-order")
    public String showHistoryOrder() {

    }*/
}
