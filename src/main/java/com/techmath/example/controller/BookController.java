package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudController;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.converter.BookConverter;
import com.techmath.example.entity.Book;
import com.techmath.example.service.BookService;
import com.techmath.example.vo.BookVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for managing books (UUID ID)")
public class BookController extends CrudController<Book, BookVO, UUID> {

    private final BookService service;
    private final BookConverter converter;

    public BookController(BookService service, BookConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    protected CrudService<Book, UUID> getService() {
        return service;
    }

    @Override
    protected Converter<Book, BookVO, UUID> getConverter() {
        return converter;
    }

}
