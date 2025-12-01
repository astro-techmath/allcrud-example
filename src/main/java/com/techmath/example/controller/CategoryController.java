package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudController;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.converter.CategoryConverter;
import com.techmath.example.entity.Category;
import com.techmath.example.service.CategoryService;
import com.techmath.example.vo.CategoryVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories (String ID)")
public class CategoryController extends CrudController<Category, CategoryVO, String> {

    private final CategoryService service;
    private final CategoryConverter converter;

    public CategoryController(CategoryService service, CategoryConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    protected CrudService<Category, String> getService() {
        return service;
    }

    @Override
    protected Converter<Category, CategoryVO, String> getConverter() {
        return converter;
    }

}
