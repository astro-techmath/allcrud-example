package com.techmath.example.converter;

import com.techmath.allcrud.converter.Converter;
import com.techmath.example.entity.Author;
import com.techmath.example.vo.AuthorVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<Author, AuthorVO, Long> {

    @Override
    public AuthorVO convertToVO(Author entity) {
        AuthorVO vo = new AuthorVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public Author convertToEntity(AuthorVO vo) {
        Author entity = new Author();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

}
