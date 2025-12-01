package com.techmath.example.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmath.allcrud.converter.AbstractCompositeIdConverter;
import com.techmath.example.entity.pk.OrderItemId;
import org.springframework.stereotype.Component;

@Component
public class StringToOrderItemIdConverter extends AbstractCompositeIdConverter<OrderItemId> {

    public StringToOrderItemIdConverter(ObjectMapper objectMapper) {
        super(OrderItemId.class, objectMapper);
    }

}
