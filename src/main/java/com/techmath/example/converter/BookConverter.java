package com.techmath.example.converter;

import com.techmath.allcrud.converter.Converter;
import com.techmath.example.entity.Author;
import com.techmath.example.entity.Book;
import com.techmath.example.entity.Category;
import com.techmath.example.vo.BookVO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookConverter implements Converter<Book, BookVO, UUID> {

    @Override
    public BookVO convertToVO(Book entity) {
        if (entity == null) {
            return null;
        }
        
        BookVO vo = new BookVO();
        vo.setId(entity.getId());
        vo.setTitle(entity.getTitle());
        vo.setIsbn(entity.getIsbn());
        vo.setPrice(entity.getPrice());
        vo.setPages(entity.getPages());
        vo.setPublicationDate(entity.getPublicationDate());
        vo.setDescription(entity.getDescription());
        
        if (entity.getAuthor() != null) {
            vo.setAuthorId(entity.getAuthor().getId());
            vo.setAuthorName(entity.getAuthor().getName());
        }
        
        if (entity.getCategory() != null) {
            vo.setCategoryId(entity.getCategory().getId());
            vo.setCategoryName(entity.getCategory().getName());
        }
        
        return vo;
    }

    @Override
    public Book convertToEntity(BookVO vo) {
        if (vo == null) {
            return null;
        }
        
        Book entity = new Book();
        entity.setId(vo.getId());
        entity.setTitle(vo.getTitle());
        entity.setIsbn(vo.getIsbn());
        entity.setPrice(vo.getPrice());
        entity.setPages(vo.getPages());
        entity.setPublicationDate(vo.getPublicationDate());
        entity.setDescription(vo.getDescription());
        
        if (vo.getAuthorId() != null) {
            Author author = new Author();
            author.setId(vo.getAuthorId());
            entity.setAuthor(author);
        }
        
        if (vo.getCategoryId() != null) {
            Category category = new Category();
            category.setId(vo.getCategoryId());
            entity.setCategory(category);
        }
        
        return entity;
    }

}
