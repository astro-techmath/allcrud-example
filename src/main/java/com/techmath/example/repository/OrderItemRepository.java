package com.techmath.example.repository;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.entity.OrderItem;
import com.techmath.example.entity.pk.OrderItemId;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends EntityRepository<OrderItem, OrderItemId> {
}
