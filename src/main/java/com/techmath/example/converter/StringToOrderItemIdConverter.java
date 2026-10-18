package com.techmath.example.converter;

import com.techmath.allcrud.converter.AbstractCompositeIdConverter;
import com.techmath.example.entity.pk.OrderItemId;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class StringToOrderItemIdConverter extends AbstractCompositeIdConverter<OrderItemId> {

    public StringToOrderItemIdConverter(ObjectMapper objectMapper) {
        super(OrderItemId.class, objectMapper);
    }

}
