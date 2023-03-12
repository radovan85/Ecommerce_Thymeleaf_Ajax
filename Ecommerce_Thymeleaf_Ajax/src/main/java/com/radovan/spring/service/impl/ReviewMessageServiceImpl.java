package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.ReviewMessageDto;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.ReviewMessageEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.ReviewMessageRepository;
import com.radovan.spring.service.ReviewMessageService;


@Service
@Transactional
public class ReviewMessageServiceImpl implements ReviewMessageService{
	
	@Autowired
	private ReviewMessageRepository reviewRepository;
	
	@Autowired
	private TempConverter tempConverter;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public ReviewMessageDto addReview(ReviewMessageDto review) {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUserId(authUser.getId());
		ReviewMessageEntity reviewEntity = tempConverter.reviewMessageDtoToEntity(review);
		reviewEntity.setCustomer(customerEntity);
		ReviewMessageEntity storedReview = reviewRepository.save(reviewEntity);
		ReviewMessageDto returnValue = tempConverter.reviewMessageEntityToDto(storedReview);
		return returnValue;
	}

	

	@Override
	public void deleteReview(Integer reviewId) {
		// TODO Auto-generated method stub
		reviewRepository.deleteById(reviewId);
		reviewRepository.flush();
	}



	@Override
	public ReviewMessageDto getReview(Integer reviewId) {
		// TODO Auto-generated method stub
		Optional<ReviewMessageEntity> reviewEntity = Optional.ofNullable(reviewRepository.getById(reviewId));
		ReviewMessageDto returnValue = null;
		
		if(reviewEntity.isPresent()) {
			returnValue = tempConverter.reviewMessageEntityToDto(reviewEntity.get());
		}
		return returnValue;
	}



	@Override
	public List<ReviewMessageDto> allReviews() {
		// TODO Auto-generated method stub
		Optional<List<ReviewMessageEntity>> allReviews = Optional.ofNullable(reviewRepository.findAll());
		List<ReviewMessageDto> returnValue = new ArrayList<>();
		
		if(!allReviews.isEmpty()) {
			for(ReviewMessageEntity reviewEntity:allReviews.get()) {
				ReviewMessageDto reviewDto = tempConverter.reviewMessageEntityToDto(reviewEntity);
				returnValue.add(reviewDto);
			}
		}
		return returnValue;
	}



	@Override
	public void deleteAllByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		reviewRepository.deleteAllByCustomerId(customerId);
		reviewRepository.flush();
	}

	

}
