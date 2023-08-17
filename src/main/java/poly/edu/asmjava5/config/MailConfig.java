package poly.edu.asmjava5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Set mail server properties
        mailSender.setHost("smtp.gmail.com"); // Replace with your SMTP server host
        mailSender.setPort(587); // Replace with the appropriate port for your SMTP server
        mailSender.setUsername("hieutranhuu1405@gmail.com"); // Replace with your email address
        mailSender.setPassword("rsrsveejjnutepvm"); // Replace with your email password
        // Additional properties, if needed
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Enable debug mode
        return mailSender;
    }
}
