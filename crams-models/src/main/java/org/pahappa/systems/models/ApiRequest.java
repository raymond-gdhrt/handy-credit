package org.pahappa.systems.models;

import org.pahappa.systems.constants.EndPointCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "payment_api_requests")
@Inheritance(strategy = InheritanceType.JOINED)
public class ApiRequest extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String endPoint;
	private EndPointCategory endPointCategory;
	private String requesterIPAddress;
	private String postBody;
	private String otherDataAsJSON;
	private String username;
	private boolean authSuccessful = false;
	private boolean tooLarge = false;
	public static final int TOO_LARGE_LOWER_LIMIT = 200;
	private String generatedMessage;

	public ApiRequest() {
		super();
	}

	/**
	 * 
	 * @param endPoint
	 * @param requesterIPAddress
	 * @param postBody
	 * @param tooLarge
	 * @param username
	 * @param authSuccessful
	 */
	public ApiRequest(String endPoint, String requesterIPAddress, String postBody, boolean tooLarge, String username,
			boolean authSuccessful) {
		super();
		this.endPoint = endPoint;
		this.requesterIPAddress = requesterIPAddress;
		this.username = username;
		this.authSuccessful = authSuccessful;
		this.postBody = postBody;
	}

	/**
	 * @return the endPoint
	 */
	@Column(name = "end_point")
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * @return the endPointCategory
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "end_point_category")
	public EndPointCategory getEndPointCategory() {
		return endPointCategory;
	}

	/**
	 * @return the generatedMessage
	 */
	@Column(name = "generated_message")
	public String getGeneratedMessage() {
		return generatedMessage;
	}

	/**
	 * @param generatedMessage the generatedMessage to set
	 */
	public void setGeneratedMessage(String generatedMessage) {
		this.generatedMessage = generatedMessage;
	}

	/**
	 * @return the postBody
	 */
	@Column(name = "post_body", length = 100000)
	public String getPostBody() {
		return postBody;
	}

	/**
	 * @return the tooLarge
	 */
	@Column(name = "post_body_too_large")
	public boolean isTooLarge() {
		return tooLarge;
	}

	/**
	 * @param tooLarge the tooLarge to set
	 */
	public void setTooLarge(boolean tooLarge) {
		this.tooLarge = tooLarge;
	}

	/**
	 * @param postBody the postBody to set
	 */
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	/**
	 * @return the requesterIPAddress
	 */
	@Column(name = "requester_ip_address")
	public String getRequesterIPAddress() {
		return requesterIPAddress;
	}

	/**
	 * @return the otherDataAsJSON
	 */
	@Column(name = "other_data_as_json", columnDefinition="TEXT")
	public String getOtherDataAsJSON() {
		return otherDataAsJSON;
	}

	/**
	 * @return the username
	 */
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	/**
	 * @return the authSuccessful
	 */
	@Column(name = "auth_successful")
	public boolean isAuthSuccessful() {
		return authSuccessful;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param authSuccessful the authSuccessful to set
	 */
	public void setAuthSuccessful(boolean authSuccessful) {
		this.authSuccessful = authSuccessful;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * @param endPointCategory the endPointCategory to set
	 */
	public void setEndPointCategory(EndPointCategory endPointCategory) {
		this.endPointCategory = endPointCategory;
	}

	/**
	 * @param requesterIPAddress the requesterIPAddress to set
	 */
	public void setRequesterIPAddress(String requesterIPAddress) {
		this.requesterIPAddress = requesterIPAddress;
	}

	/**
	 * @param otherDataAsJSON the otherDataAsJSON to set
	 */
	public void setOtherDataAsJSON(String otherDataAsJSON) {
		this.otherDataAsJSON = otherDataAsJSON;
	}

	@Transient
	public String getSummary() {
		return this.tooLarge ? this.postBody.substring(0, TOO_LARGE_LOWER_LIMIT) + "..." : this.postBody;
	}
}