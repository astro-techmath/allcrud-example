package com.techmath.example.repository;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends EntityRepository<Category, String> {
}
