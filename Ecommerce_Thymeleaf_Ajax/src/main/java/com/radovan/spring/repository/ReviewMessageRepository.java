package com.radovan.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.ReviewMessageEntity;

@Repository
public interface ReviewMessageRepository extends JpaRepository<ReviewMessageEntity, Integer> {

	@Modifying
	@Query(value = "delete from reviews where customer_id = :customerId",nativeQuery = true)
	void deleteAllByCustomerId(@Param ("customerId") Integer customerId);
	
}
