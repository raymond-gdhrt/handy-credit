package com.handycredit.systems.core.utils;

import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.net.URI;
import java.util.Properties;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.CustomLogger;

public class EmailService {

    private String username;
    private String password;

    private final Properties prop;

    private final SystemSetting systemSetting;

    public EmailService() {

        systemSetting = ApplicationContextProvider.getBean(SystemSettingService.class).getSystemSettings();

        CustomLogger.log(EmailService.class, systemSetting.toString());

        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", systemSetting.getMailSenderSmtpHost());
        prop.put("mail.smtp.port", systemSetting.getMailSenderSmtpPort());
        prop.put("mail.smtp.ssl.trust", systemSetting.getMailSenderSmtpHost());

        this.username = systemSetting.getMailSenderAddress();
        this.password = systemSetting.getMailSenderPassword();
        //        prop = new Properties();
        //        prop.put("mail.smtp.auth", true);
        //        prop.put("mail.smtp.starttls.enable", "true");
        //        prop.put("mail.smtp.host", "smtp.gmail.com");
        //        prop.put("mail.smtp.port", "587");
        //        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //
        //        this.username = "revivalgateway@gmail.com";
        //        this.password = "1234revivalgateway";
        //    }
    }

    public static void main(String... args) {
        try {
            new EmailService()
                    .sendMail("raymond@pahappa.com", "TEST EMAIL", "This is my <b>Test</b> email");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMail(String toAddress, String subject, String body) throws Exception {

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message, username, password);
    }

    private File getFile() throws Exception {
        URI uri = this.getClass()
                .getClassLoader()
                .getResource("attachment.txt")
                .toURI();
        return new File(uri);
    }

}
