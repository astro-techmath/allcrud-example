package com.techmath.example.service;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.entity.OrderItem;
import com.techmath.example.entity.pk.OrderItemId;
import com.techmath.example.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService extends CrudService<OrderItem, OrderItemId> {

    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EntityRepository<OrderItem, OrderItemId> getRepository() {
        return repository;
    }

}
