package poly.edu.asmjava5.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import poly.edu.asmjava5.auth.Role;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    @NotEmpty(message = "Không được bỏ trống trường fullname")
    private String fullname;
    @Email(message = "Vui lòng nhập đúng định dạng gmail")
    @NotEmpty(message = "Không được bỏ trống trường email")
    private String email;
    @NotEmpty(message = "Không được bỏ trống trường password")
    @Length(min = 10, message = "Độ dài tối thiểu phải 10 ký tự")
    private String password;
    @NotEmpty(message = "Không được bỏ trống trường điện thoại")
    @Length(max = 10, message = "Độ dài nhỏ hơn hoặc bằng 10 số")
    private String phone;

    @NotEmpty(message = "Không được bỏ trống trường username")
    private String username;

    private Role role;

    private boolean active = true;


    private Boolean rememberMe = false;
}
