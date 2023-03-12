package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.ReviewMessageDto;

public interface ReviewMessageService {

	ReviewMessageDto addReview(ReviewMessageDto review);

	ReviewMessageDto getReview(Integer reviewId);

	List<ReviewMessageDto> allReviews();

	void deleteReview(Integer reviewId);
	
	void deleteAllByCustomerId(Integer customerId);
}
