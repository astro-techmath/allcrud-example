package com.techmath.example.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techmath.allcrud.entity.AbstractEntityVO;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Book Value Object
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookVO implements AbstractEntityVO<UUID> {

    private UUID id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @NotBlank
    @Size(min = 10, max = 17)
    private String isbn;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Integer pages;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @Size(max = 1000)
    private String description;

    @NotNull
    private Long authorId;

    private String authorName;

    @NotNull
    private String categoryId;

    private String categoryName;

}
