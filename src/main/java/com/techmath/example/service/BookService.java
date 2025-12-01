package com.techmath.example.service;

import com.techmath.allcrud.exception.BusinessException;
import com.techmath.allcrud.repository.EntityRepository;
import com.techmath.allcrud.service.CrudService;
import com.techmath.example.entity.Book;
import com.techmath.example.repository.AuthorRepository;
import com.techmath.example.repository.BookRepository;
import com.techmath.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookService extends CrudService<Book, UUID> {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository repository, 
                      AuthorRepository authorRepository,
                      CategoryRepository categoryRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected EntityRepository<Book, UUID> getRepository() {
        return repository;
    }

    @Override
    @Transactional
    public Book create(Book entity) {
        validateBook(entity);
        return super.create(entity);
    }

    @Override
    @Transactional
    public Book update(UUID id, Book toUpdate) {
        validateBook(toUpdate);
        return super.update(id, toUpdate);
    }

    private void validateBook(Book book) {
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            var author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new BusinessException("Author not found with ID: " + book.getAuthor().getId()));
            
            if (!author.getActive()) {
                throw new BusinessException("Cannot create book for inactive author");
            }
            
            book.setAuthor(author);
        }
        
        if (book.getCategory() != null && book.getCategory().getId() != null) {
            var category = categoryRepository.findById(book.getCategory().getId())
                    .orElseThrow(() -> new BusinessException("Category not found with ID: " + book.getCategory().getId()));
            
            book.setCategory(category);
        }
    }

}
