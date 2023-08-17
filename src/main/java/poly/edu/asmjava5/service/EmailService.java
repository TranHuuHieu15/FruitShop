package poly.edu.asmjava5.service;

import jakarta.servlet.ServletContext;
import poly.edu.asmjava5.domain.Account;

public interface EmailService {
    void sendMail(Account nguoiNhan, String type);
}
