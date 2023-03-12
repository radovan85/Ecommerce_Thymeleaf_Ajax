package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.CustomerOrderEntity;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Integer> {

	@Query(value = "select * from customer_orders where customer_id = :customerId",nativeQuery = true)
	List<CustomerOrderEntity> findAllByCustomerId(@Param ("customerId") Integer customerId);

}
