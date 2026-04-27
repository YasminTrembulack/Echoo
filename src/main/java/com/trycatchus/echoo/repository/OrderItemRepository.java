package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycatchus.echoo.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
