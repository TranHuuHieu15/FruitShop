package poly.edu.asmjava5.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.service.AccountService;
import poly.edu.asmjava5.service.EmailService;

import java.io.UnsupportedEncodingException;

@Service
public class MailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    AccountService accountService;

    @Autowired
    BCryptPasswordEncoder pe;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private final static String EMAIL_WELCOME_SUBJECT = "Chào mừng bạn đến với Fruit Shop";
    private final static String EMAIL_FORGOT_PASSWORD = "Fruit Shop - Mật khẩu mới";

    @Override
    public void sendMail(Account nguoiNhan, String type){
        String content = null;
        String subject = null;
        switch (type) {
            case "welcome": {
                subject = EMAIL_WELCOME_SUBJECT;
                content = "Chào mừng " + nguoiNhan.getFullname() + " đến với Fruit Shop";
                break;
            }
            case "forgot": {
                subject = EMAIL_FORGOT_PASSWORD;
                content = "Chào " + nguoiNhan.getFullname() + ", Đây là mật khẩu mới của bạn: " + nguoiNhan.getPassword();
                nguoiNhan.setPassword(pe.encode(nguoiNhan.getPassword()));
                accountService.save(nguoiNhan);
                break;
            }
            default:
                subject = "Fruit Shop - Cảnh báo!";
                content = "Có lẽ mail này không quan trọng, đừng quan tâm về nó!";
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hieutranhuu1405@gmail.com");
        message.setTo(nguoiNhan.getEmail());
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
