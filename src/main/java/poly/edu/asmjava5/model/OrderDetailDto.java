package poly.edu.asmjava5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long orderDetailId;
    private Long orderId;
    private int productId;
    private int quantity;
    private double unitPrice;
}
