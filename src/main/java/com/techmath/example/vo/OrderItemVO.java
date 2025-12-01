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

    private UUID orderId;
    private UUID bookId;

    private Integer quantity;
    private BigDecimal unitPrice;

    @Override
    public OrderItemId getId() {
        if (orderId != null && bookId != null) {
            return new OrderItemId(orderId, bookId);
        }
        return null;
    }

    @Override
    public void setId(OrderItemId id) {
        if (id != null) {
            this.orderId = id.getOrderId();
            this.bookId = id.getBookId();
        }
    }

}
