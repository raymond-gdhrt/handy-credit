package com.handycredit.systems.core.utils;

import com.handycredit.systems.core.services.SystemSettingService;
import com.handycredit.systems.models.SystemSetting;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

public class EMailClient {

    private static String SMTPHOST = "smtp.gmail.com";
    private static int SMTP_PORT = 587;
    private static String SMTP_PASSWORD = "";
    private static String SMTP_ADDRESS = "";

    private static String fromAddress = "mail.pahappa@gmail.com";

    public static void initMailSettings() {
        SystemSetting appSetting = ApplicationContextProvider.getBean(SystemSettingService.class).getSystemSettings();

        if (appSetting != null) {
            SMTPHOST = appSetting.getMailSenderSmtpHost();
            SMTP_PORT = Integer.parseInt(appSetting.getMailSenderSmtpPort());
            SMTP_ADDRESS = appSetting.getMailSenderAddress();
            SMTP_PASSWORD = appSetting.getMailSenderPassword();
        }
    }

    public void sendHtmlEmail(String toAddress, String subject, String body) {
        try {
            Session session = getSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setContent(body, "text/html");
            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Session getSession() {
        initMailSettings();
        Properties props = System.getProperties();
        props.put("mail.smtp.host", SMTPHOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_ADDRESS, SMTP_PASSWORD);
            }
        });

        return session;
    }


}
