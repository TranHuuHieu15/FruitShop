package poly.edu.asmjava5.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {

    private Long categoryId;

    @NotEmpty(message = "Không được để trống tên!")
    @Length(min = 5, message = "Độ dài tối thiểu là 5 ký tự")
    private String name;

    private Boolean idEdit = false;
}
