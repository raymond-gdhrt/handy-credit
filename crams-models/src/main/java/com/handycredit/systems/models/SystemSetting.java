/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handycredit.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;

/**
 *
 * @author Ray Gdhrt
 */
@Entity
@Table(name = "system_settings")
public class SystemSetting extends BaseEntity {

    private String systemName;
    private String mailSenderSmtpHost = "smtp.gmail.com";
    private String mailSenderSmtpPort = "587";
    private String mailSenderAddress="credithandy@gmail.com";
    private String mailSenderPassword="pass@handycredit";
    private String themeName = "blue";
    private String defaultCurrencyCode = "USD";
    private int defaultCurrencyDecimalPlaces = 1;

    @Column(name = "system_name", length = 20)
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Column(name = "mail_sender_smtp_host", length = 20)
    public String getMailSenderSmtpHost() {
        return mailSenderSmtpHost;
    }

    public void setMailSenderSmtpHost(String mailSenderSmtpHost) {
        this.mailSenderSmtpHost = mailSenderSmtpHost;
    }

    @Column(name = "mail_sender_smtp_port", length = 20)
    public String getMailSenderSmtpPort() {
        return mailSenderSmtpPort;
    }

    public void setMailSenderSmtpPort(String mailSenderSmtpPort) {
        this.mailSenderSmtpPort = mailSenderSmtpPort;
    }

    @Column(name ="mail_sender_address",length = 50)
    public String getMailSenderAddress() {
        return mailSenderAddress;
    }

    public void setMailSenderAddress(String mailSenderAddress) {
        this.mailSenderAddress = mailSenderAddress;
    }

     @Column(name ="mail_sender_password",length = 100)
    public String getMailSenderPassword() {
        return mailSenderPassword;
    }

    public void setMailSenderPassword(String mailSenderPassword) {
        this.mailSenderPassword = mailSenderPassword;
    }

    @Column(name ="default_currency_code",length = 5)
    public String getDefaultCurrencyCode() {
        return defaultCurrencyCode;
    }

    public void setDefaultCurrencyCode(String defaultCurrencyCode) {
        this.defaultCurrencyCode = defaultCurrencyCode;
    }

     @Column(name ="default_currency_decimal_places",length = 2)
    public int getDefaultCurrencyDecimalPlaces() {
        return defaultCurrencyDecimalPlaces;
    }

    public void setDefaultCurrencyDecimalPlaces(int defaultCurrencyDecimalPlaces) {
        this.defaultCurrencyDecimalPlaces = defaultCurrencyDecimalPlaces;
    }

}
