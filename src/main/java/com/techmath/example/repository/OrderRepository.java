package com.techmath.example.repository;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends EntityRepository<Order, UUID> {
}
