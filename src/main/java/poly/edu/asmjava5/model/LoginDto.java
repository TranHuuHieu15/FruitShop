package poly.edu.asmjava5.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto implements Serializable {

    @NotEmpty(message = "Không được bỏ trống trường password")
    @Length(min = 10, message = "Độ dài tối thiểu phải 10 ký tự")
    private String password;

    @NotEmpty(message = "Không được bỏ trống trường username")
    private String username;

    private Boolean rememberMe = false;
}
