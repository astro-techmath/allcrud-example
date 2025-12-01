package com.techmath.example.entity;

import com.techmath.allcrud.entity.AbstractEntity;
import com.techmath.example.entity.pk.OrderItemId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item_tb")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class OrderItem implements AbstractEntity<OrderItemId> {

    @EmbeddedId
    private OrderItemId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

}
