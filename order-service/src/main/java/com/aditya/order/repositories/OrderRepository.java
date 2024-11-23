package com.aditya.order.repositories;

import com.aditya.order.enums.OrderStatus;
import com.aditya.order.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

	OrderEntity findByUserIdAndStatus(Integer userId, OrderStatus orderStatus);
}
