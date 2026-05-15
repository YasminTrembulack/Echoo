package com.trycatchus.echoo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycatchus.echoo.models.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
