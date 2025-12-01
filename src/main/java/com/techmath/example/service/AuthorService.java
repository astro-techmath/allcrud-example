package com.techmath.example.service;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.entity.Author;
import com.techmath.example.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends CrudService<Author, Long> {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EntityRepository<Author, Long> getRepository() {
        return repository;
    }

}
