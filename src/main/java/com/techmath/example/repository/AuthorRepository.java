package com.techmath.example.repository;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends EntityRepository<Author, Long> {
}
