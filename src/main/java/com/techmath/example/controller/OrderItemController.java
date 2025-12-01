package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudController;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.converter.OrderItemConverter;
import com.techmath.example.entity.OrderItem;
import com.techmath.example.entity.pk.OrderItemId;
import com.techmath.example.service.OrderItemService;
import com.techmath.example.vo.OrderItemVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-items")
@Tag(name = "Order Items", description = "Endpoints for managing order items (Composite Key)")
public class OrderItemController extends CrudController<OrderItem, OrderItemVO, OrderItemId> {

    private final OrderItemService service;
    private final OrderItemConverter converter;

    public OrderItemController(OrderItemService service, OrderItemConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    protected CrudService<OrderItem, OrderItemId> getService() {
        return service;
    }

    @Override
    protected Converter<OrderItem, OrderItemVO, OrderItemId> getConverter() {
        return converter;
    }

}
