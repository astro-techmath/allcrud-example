package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudController;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.converter.OrderConverter;
import com.techmath.example.entity.Order;
import com.techmath.example.service.OrderService;
import com.techmath.example.vo.OrderVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "Endpoints for managing order (UUID ID)")
public class OrderController extends CrudController<Order, OrderVO, UUID> {

    private final OrderService service;
    private final OrderConverter converter;

    public OrderController(OrderService service, OrderConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    protected CrudService<Order, UUID> getService() {
        return service;
    }

    @Override
    protected Converter<Order, OrderVO, UUID> getConverter() {
        return converter;
    }

}
