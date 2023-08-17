package poly.edu.asmjava5.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Long productId;
    @NotEmpty(message = "Không được để trống tên sản phẩm")
    @Length(min = 5, message = "Độ dài của tên tối thiểu là 5 ký tự" )
    private String name;
    @NotNull(message = "Không được để trống số lượng")
    @Min(value = 0, message="Số lượng phải lớn hơn hoặc bằng 0")
    private int quantity;
    @NotNull(message = "Không được để trống giá tiền")
    @Min(value = 0, message="Giá phải lớn hơn hoặc bằng 0")
    private double unitPrice;
    private String image;

    private MultipartFile imageFile;

    private String description;
    @NotNull(message = "Không được để trống giảm giá")
    @Min(value = 0, message="Giảm giá phải lớn hơn hoặc bằng 0")
    private double discount;
    private Date enteredDate;
    private short status;
    private Long categoryId;
}
