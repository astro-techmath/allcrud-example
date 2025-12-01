package com.techmath.example.entity;

import com.techmath.allcrud.entity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Book entity - demonstrates UUID as ID type
 */
@Entity
@Table(name = "book_tb")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class Book implements AbstractEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(min = 1, max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Size(min = 10, max = 17)
    @Column(unique = true, nullable = false, length = 17)
    private String isbn;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer pages;

    @Past
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
