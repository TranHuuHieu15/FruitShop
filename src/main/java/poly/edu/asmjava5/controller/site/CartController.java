package poly.edu.asmjava5.controller.site;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.asmjava5.domain.*;
import poly.edu.asmjava5.repository.CartRepository;
import poly.edu.asmjava5.service.AccountService;
import poly.edu.asmjava5.service.OrderDetailService;
import poly.edu.asmjava5.service.OrderService;
import poly.edu.asmjava5.service.ProductService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController{

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    HttpSession session;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    AccountService accountService;


    // hiển thị cart
    @GetMapping("/cart")
    public String getCart(
            Model model
    ) {
        List<Cart> cart = cartRepository.findAll();
        int totalQuantity = 0;
        double totalPrice = 0.0;
        for (Cart cartItem : cart) {
            totalQuantity += cartItem.getQuantity();
            totalPrice += cartItem.getTotal();
        }
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "site/cart";
    }

    //! cái ni để dùng nút add to cart dựa vào id của product
    @GetMapping("/cart/add")
    public String addCartWithShop(
            Model model,
            @RequestParam("id") Long productId
    ) {
        int quantity = 1;
        Optional<Product> product = productService.findById(productId);
        if (product.isPresent()) {
            Cart cart = cartRepository.findByProductId(productId);

            if (cart != null) {
                // Nếu cart đã tồn tại, cập nhật thông tin
                cart.setQuantity(cart.getQuantity() + quantity);
                cart.setTotal(cart.getQuantity() * product.get().getUnitPrice());
            } else {
                // Nếu cart chưa tồn tại, tạo mới và lưu thông tin
                cart = new Cart();
                cart.setImage(product.get().getImage());
                cart.setProductId(product.get().getProductId());
                cart.setQuantity(quantity);
                cart.setNameProduct(product.get().getName());
                cart.setPrice(product.get().getUnitPrice());
                cart.setTotal(quantity * product.get().getUnitPrice());
            }
            cartRepository.save(cart);
            return "redirect:/cart";
        }
        return "site/shop";
    }

    // lưu vào cart
    @PostMapping("/cart/add")
    public String addCart(
            Model model,
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "quantity") Integer quantity,
            RedirectAttributes attributes
            ){
        Optional<Product> product = productService.findById(productId);
        if (product.isPresent()) {
            // cái ni để chủ yếu để lưu vô model để lưu vô order với orderdetails
            Long pId = product.get().getProductId();
            attributes.addFlashAttribute("pId",pId);
            System.out.println("Product Id: "+pId);

            /// Tìm kiếm cart dựa trên productId
            Cart cart = cartRepository.findByProductId(productId);

            if (cart != null) {
                // Nếu cart đã tồn tại, cập nhật thông tin
                cart.setQuantity(cart.getQuantity() + quantity);
                cart.setTotal(cart.getQuantity() * product.get().getUnitPrice());
            } else {
                // Nếu cart chưa tồn tại, tạo mới và lưu thông tin
                cart = new Cart();
                cart.setImage(product.get().getImage());
                cart.setProductId(product.get().getProductId());
                cart.setQuantity(quantity);
                cart.setNameProduct(product.get().getName());
                cart.setPrice(product.get().getUnitPrice());
                cart.setTotal(quantity * product.get().getUnitPrice());
            }
            cartRepository.save(cart);
            return "redirect:/cart";
        }
        return "redirect:/single-product";
    }

    // xóa sản phẩm khỏi cart
    @GetMapping("/cart/delete/{cartId}")
    public String deleteProductInCart(
            Model model,
            @PathVariable("cartId") Long cartId
    ) {
        cartRepository.deleteById(cartId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkoutCart(
            Model model,
            RedirectAttributes attributes
    ) {
//        Account currentUser = (Account) session.getAttribute("user");
        String username = "";
        if(session.getAttribute("username") == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = authentication.getName();
        }else{
            username =(String) session.getAttribute("username");
        }

        Account account = accountService.findById(username).orElse(null);
        System.err.println("Account is:" + account);
        if (account == null) {
            model.addAttribute("message", "Vui lòng đăng nhập trước khi thanh toán");
            return "forward:/login";
        }
        List<Cart> cartItems = cartRepository.findAll();
//        Long productId = (Long) attributes.getFlashAttributes().get("pId");
//        Product product = productService.findById(productId).orElse(null);

        // Tạo đối tượng Order
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUsername(account); // Thay currentUser bằng đối tượng người dùng đang đặt hàng
        order.setStatus((short) 0);
        double totalAmount = 0.0;

        // Lưu Order vào cơ sở dữ liệu
        orderService.save(order);

        // Tạo OrderDetail cho từng sản phẩm trong giỏ hàng
        for (Cart cartItem : cartItems) {
            if (cartItem.getCartId() != null) {
                Optional<Product> productOptional = productService.findById(cartItem.getProductId());
                if (productOptional.isPresent()) {
                    Product product2 = productOptional.get();
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setQuantity(cartItem.getQuantity());
                    orderDetail.setUnitPrice(cartItem.getPrice());
                    orderDetail.setProduct(product2);
                    orderDetail.setOrder(order);
                    // Lưu OrderDetail vào cơ sở dữ liệu
                    orderDetailService.save(orderDetail);

                    totalAmount += cartItem.getTotal();
                }
            }
        }

        // Cập nhật tổng số tiền của đơn hàng
        order.setAmount(totalAmount);
        orderService.save(order);
        // Xóa giỏ hàng sau khi đã đặt hàng thành công
        cartRepository.deleteAll();
        return "redirect:/history-order";
    }

}
