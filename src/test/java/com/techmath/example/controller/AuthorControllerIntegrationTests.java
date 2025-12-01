package com.techmath.example.controller;

import com.techmath.allcrud.controller.CrudControllerIntegrationTests;
import com.techmath.allcrud.converter.Converter;
import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.converter.AuthorConverter;
import com.techmath.example.entity.Author;
import com.techmath.example.repository.AuthorRepository;
import com.techmath.example.vo.AuthorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests extends CrudControllerIntegrationTests<Author, AuthorVO, Long> {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private AuthorConverter converter;

    public AuthorControllerIntegrationTests() {
        super(AuthorVO.class, Long.class, AuthorController.class);
    }

    @Override
    protected EntityRepository<Author, Long> getRepository() {
        return repository;
    }

    @Override
    public Converter<Author, AuthorVO, Long> getConverter() {
        return converter;
    }

}
