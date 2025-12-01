package com.techmath.example.vo;

import com.techmath.allcrud.entity.AbstractEntityVO;
import com.techmath.example.entity.pk.OrderItemId;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class OrderItemVO implements AbstractEntityVO<OrderItemId> {

    private OrderItemId id;

    private UUID orderId;
    private UUID bookId;

    private Integer quantity;
    private BigDecimal unitPrice;

}
