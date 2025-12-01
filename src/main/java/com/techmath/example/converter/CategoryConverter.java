package com.techmath.example.converter;

import com.techmath.allcrud.converter.Converter;
import com.techmath.example.entity.Category;
import com.techmath.example.vo.CategoryVO;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<Category, CategoryVO, String> {

    @Override
    public CategoryVO convertToVO(Category entity) {
        if (entity == null) {
            return null;
        }
        
        CategoryVO vo = new CategoryVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setDescription(entity.getDescription());
        return vo;
    }

    @Override
    public Category convertToEntity(CategoryVO vo) {
        if (vo == null) {
            return null;
        }
        
        Category entity = new Category();
        entity.setId(vo.getId());
        entity.setName(vo.getName());
        entity.setDescription(vo.getDescription());
        return entity;
    }

}
