package md.akdev_service_management.sm.config;

import md.akdev_service_management.sm.controllers.ConfigController;
import md.akdev_service_management.sm.services.config.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private final ConfigService configService;

    public MailConfig(ConfigService configService) {
        this.configService = configService;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configService.findByName("mailServer").getValue());
        mailSender.setPort(587);

        mailSender.setUsername(configService.findByName("email").getValue());
        mailSender.setPassword(configService.findByName("password").getValue());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

