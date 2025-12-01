package com.techmath.example.vo;

import com.techmath.allcrud.entity.AbstractEntityVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Category Value Object
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class CategoryVO implements AbstractEntityVO<String> {

    @NotBlank
    @Size(min = 2, max = 50)
    private String id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

}
