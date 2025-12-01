package com.techmath.example.service;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.allcrud.service.CrudService;
import com.techmath.allcrud.service.CrudServiceTests;
import com.techmath.example.entity.Author;
import com.techmath.example.repository.AuthorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests extends CrudServiceTests<Author, Long> {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorService service;

    public AuthorServiceTests() {
        super(Author.class, Long.class);
    }

    @Override
    protected EntityRepository<Author, Long> getRepository() {
        return repository;
    }

    @Override
    protected CrudService<Author, Long> getService() {
        return service;
    }

}
