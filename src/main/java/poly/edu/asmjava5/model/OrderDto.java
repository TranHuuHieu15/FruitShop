package poly.edu.asmjava5.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private Long orderId;

    @NotEmpty(message = "Không được bỏ trống trường username")
    private String username;
    private Date orderDate;

    @NotNull(message = "Không được bỏ trống trường tổng giá tiền")
    @Min(value = 0, message = "Giá trị nhỏ nhất phải lớn hơn hoặc bằng 0 đồng")
    private double amount;

    private short status;
}
