package com.techmath.example.service;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.entity.Order;
import com.techmath.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService extends CrudService<Order, UUID> {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EntityRepository<Order, UUID> getRepository() {
        return repository;
    }

}
