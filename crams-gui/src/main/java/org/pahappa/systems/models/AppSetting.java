package org.pahappa.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "app_setting")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppSetting extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private float referrerPercentageCharge;
    private String egosmsUrl;
    private String paymentUrl;
    private String egoSmsApiUsername;
    private String egoSmsApiPassword;
    private String paymentApiUsername;
    private String paymentApiPassword;
    private String balanceCode="UGX-MTNMM";

    /**
     * @return the referrerPercentageCharge
     */
    @Column(name = "referrer_percentage_charge", nullable = false, length = 10)
    public float getReferrerPercentageCharge() {
        return referrerPercentageCharge;
    }

    /**
     * @param referrerPercentageCharge the referrerPercentageCharge to set
     */
    public void setReferrerPercentageCharge(float referrerPercentageCharge) {
        this.referrerPercentageCharge = referrerPercentageCharge;
    }

    /**
     * @return the egosmsUrl
     */
    @Column(name = "egosms_url", nullable = false, length = 100)
    public String getEgosmsUrl() {
        return egosmsUrl;
    }

    /**
     * @param egosmsUrl the egosmsUrl to set
     */
    public void setEgosmsUrl(String egosmsUrl) {
        this.egosmsUrl = egosmsUrl;
    }

    /**
     * @return the paymentUrl
     */
    @Column(name = "payment_url", nullable = false, length = 100)
    public String getPaymentUrl() {
        return paymentUrl;
    }

    /**
     * @param paymentUrl the paymentUrl to set
     */
    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    /**
     * @return the egoSmsApiUsername
     */
    @Column(name = "ego_sms_api_username", nullable = false, length = 100)
    public String getEgoSmsApiUsername() {
        return egoSmsApiUsername;
    }

    /**
     * @param egoSmsApiUsername the egoSmsApiUsername to set
     */
    public void setEgoSmsApiUsername(String egoSmsApiUsername) {
        this.egoSmsApiUsername = egoSmsApiUsername;
    }

    /**
     * @return the egoSmsApiPassword
     */
    @Column(name = "ego_sms_api_password", nullable = false, length = 100)
    public String getEgoSmsApiPassword() {
        return egoSmsApiPassword;
    }

    /**
     * @param egoSmsApiPassword the egoSmsApiPassword to set
     */
    public void setEgoSmsApiPassword(String egoSmsApiPassword) {
        this.egoSmsApiPassword = egoSmsApiPassword;
    }

    /**
     * @return the paymentApiUsername
     */
    @Column(name = "payment_api_username", length = 100)
    public String getPaymentApiUsername() {
        return paymentApiUsername;
    }

    /**
     * @param paymentApiUsername the paymentApiUsername to set
     */
    public void setPaymentApiUsername(String paymentApiUsername) {
        this.paymentApiUsername = paymentApiUsername;
    }

    /**
     * @return the paymentApiPassword
     */
    @Column(name = "payment_api_password", length = 100)
    public String getPaymentApiPassword() {
        return paymentApiPassword;
    }

    /**
     * @param paymentApiPassword the paymentApiPassword to set
     */
    public void setPaymentApiPassword(String paymentApiPassword) {
        this.paymentApiPassword = paymentApiPassword;
    }

    @Column(name = "balanca_code", length = 10)
    public String getBalanceCode() {
        return balanceCode;
    }

    public void setBalanceCode(String balanceCode) {
        this.balanceCode = balanceCode;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof AppSetting && (super.getId() != null) ? super.getId().equals(((AppSetting) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }
}
