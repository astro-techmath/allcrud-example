package com.techmath.example.converter;

import com.techmath.allcrud.converter.Converter;
import com.techmath.example.entity.Book;
import com.techmath.example.entity.Order;
import com.techmath.example.entity.OrderItem;
import com.techmath.example.entity.pk.OrderItemId;
import com.techmath.example.vo.OrderItemVO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter implements Converter<OrderItem, OrderItemVO, OrderItemId> {

    @Override
    public OrderItemVO convertToVO(OrderItem entity) {
        OrderItemVO vo = new OrderItemVO();
        vo.setId(entity.getId());

        if (entity.getId() != null) {
            vo.setOrderId(entity.getId().getOrderId());
            vo.setBookId(entity.getId().getBookId());
        }

        vo.setQuantity(entity.getQuantity());
        vo.setUnitPrice(entity.getUnitPrice());
        return vo;
    }

    @Override
    public OrderItem convertToEntity(OrderItemVO vo) {
        OrderItem entity = new OrderItem();

        if (vo.getId() != null) {
            entity.setId(vo.getId());
        } else if (vo.getOrderId() != null && vo.getBookId() != null) {
            entity.setId(new OrderItemId(vo.getOrderId(), vo.getBookId()));
        }

        if (entity.getId() != null) {
            Order orderProxy = new Order();
            orderProxy.setId(entity.getId().getOrderId());
            entity.setOrder(orderProxy);

            Book bookProxy = new Book();
            bookProxy.setId(entity.getId().getBookId());
            entity.setBook(bookProxy);
        }

        entity.setQuantity(vo.getQuantity());
        entity.setUnitPrice(vo.getUnitPrice());
        return entity;
    }

}
