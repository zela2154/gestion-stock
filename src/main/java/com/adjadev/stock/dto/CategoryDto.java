package com.adjadev.stock.dto;

import com.adjadev.stock.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDto {
    private Integer id;
    private String code;
    private String designation;
    private Integer idEntreprise;
    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category){
        if(category ==null){
            return  null;
        }
        //Mapping de Category vers CategoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();
    };

    public static Category toEntity(CategoryDto categoryDto){
        if(categoryDto == null){
            return null;
        }
        /*return Category.builder()
                //.id(categoryDto.getId())
                .code(categoryDto.getCode())
                .designation(categoryDto.getDesignation())
                .build();*/
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        return  category;
    };
}
