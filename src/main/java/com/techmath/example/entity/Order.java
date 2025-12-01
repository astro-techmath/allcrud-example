package com.techmath.example.entity;

import com.techmath.allcrud.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_tb")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class Order implements AbstractEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

}
