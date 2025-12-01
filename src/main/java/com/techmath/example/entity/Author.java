package com.techmath.example.entity;

import com.techmath.allcrud.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Author entity - demonstrates Long ID type and SoftDeletable
 */
@Entity
@Table(name = "author_tb")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class Author extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Email
    @Column(unique = true, length = 100)
    private String email;

    @Size(max = 500)
    @Column(length = 500)
    private String bio;

    @Column(nullable = false)
    private Boolean active = true;

}
