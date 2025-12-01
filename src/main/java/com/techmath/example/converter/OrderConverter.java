package com.techmath.example.converter;

import com.techmath.allcrud.converter.Converter;
import com.techmath.example.entity.Order;
import com.techmath.example.vo.OrderVO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderConverter implements Converter<Order, OrderVO, UUID> {

    @Override
    public OrderVO convertToVO(Order entity) {
        OrderVO vo = new OrderVO();
        vo.setId(entity.getId());
        vo.setCustomerName(entity.getCustomerName());
        vo.setOrderDate(entity.getOrderDate());
        return vo;
    }

    @Override
    public Order convertToEntity(OrderVO vo) {
        Order entity = new Order();
        entity.setId(vo.getId());
        entity.setCustomerName(vo.getCustomerName());
        entity.setOrderDate(vo.getOrderDate());
        return entity;
    }

}
