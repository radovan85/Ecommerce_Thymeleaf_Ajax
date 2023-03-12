package com.radovan.spring.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReviewMessageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer reviewMessageId;

	private String text;

	private Timestamp date;

	private Integer customerId;

	public Integer getReviewMessageId() {
		return reviewMessageId;
	}

	public void setReviewMessageId(Integer reviewMessageId) {
		this.reviewMessageId = reviewMessageId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
