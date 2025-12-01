package com.techmath.example.service;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.entity.Category;
import com.techmath.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudService<Category, String> {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EntityRepository<Category, String> getRepository() {
        return repository;
    }

}
