package com.techmath.example.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techmath.allcrud.entity.AbstractEntityVO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Author Value Object
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorVO implements AbstractEntityVO<Long> {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Email
    private String email;

    @Size(max = 500)
    private String bio;

    private Boolean active;

}
