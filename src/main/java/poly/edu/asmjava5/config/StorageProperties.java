package poly.edu.asmjava5.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Data
//@Component
public class StorageProperties {
    private String location; // vị trí để lưu file
}
