package com.techmath.example.vo;

import com.techmath.allcrud.entity.AbstractEntityVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderVO implements AbstractEntityVO<UUID> {
    private UUID id;
    private String customerName;
    private LocalDateTime orderDate;
}
