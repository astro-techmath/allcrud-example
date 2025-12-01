package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudController;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.converter.AuthorConverter;
import com.techmath.example.entity.Author;
import com.techmath.example.service.AuthorService;
import com.techmath.example.vo.AuthorVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Endpoints for managing authors (Long ID)")
public class AuthorController extends CrudController<Author, AuthorVO, Long> {

    private final AuthorService service;
    private final AuthorConverter converter;

    @Override
    protected CrudService<Author, Long> getService() {
        return service;
    }

    @Override
    protected Converter<Author, AuthorVO, Long> getConverter() {
        return converter;
    }

}
