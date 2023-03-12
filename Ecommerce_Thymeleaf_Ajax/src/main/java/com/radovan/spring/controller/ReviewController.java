package com.radovan.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.ReviewMessageDto;
import com.radovan.spring.service.ReviewMessageService;

@Controller
@RequestMapping(value = "/review")
public class ReviewController {
	
	@Autowired
	private ReviewMessageService reviewService;

	@RequestMapping(value = "/sendReview",method = RequestMethod.GET)
	public String renderReviewForm(ModelMap map) {
		
		ReviewMessageDto review = new ReviewMessageDto();
		map.put("review", review);
		return "fragments/reviewForm :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/sendReview",method = RequestMethod.POST)
	public String sendReview(@ModelAttribute ("review") ReviewMessageDto review) {
		reviewService.addReview(review);
		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/reviewSent",method = RequestMethod.GET)
	public String reviewResult() {
		return "fragments/reviewSent :: ajaxLoadedContent";
	}
}
