package ragen.common.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${mail.account}")
    private String account;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;

    @Value("${mail.smtp.ssl.enable}")
    private String sslenable;

    @Value("${mail.smtp.starttls.enable}")
    private String starttlsenable;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setUsername(account);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.starttls.enable", starttlsenable);
        properties.setProperty("mail.debug", debug);
        properties.setProperty("mail.smtp.ssl.trust",host);
        properties.setProperty("mail.smtp.ssl.enable",sslenable);
        return properties;
    }
}
