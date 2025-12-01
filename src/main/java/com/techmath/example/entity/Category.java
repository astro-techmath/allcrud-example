package com.techmath.example.entity;

import com.techmath.allcrud.entity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Category entity - demonstrates String as ID type (business key)
 */
@Entity
@Table(name = "category_tb")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class Category implements AbstractEntity<String> {

    @Id
    @Column(length = 50)
    private String id; // Using category code as ID (e.g., "FICTION", "SCIENCE")

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

}
