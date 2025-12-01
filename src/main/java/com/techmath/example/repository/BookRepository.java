package com.techmath.example.repository;

import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.example.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends EntityRepository<Book, UUID> {
}
