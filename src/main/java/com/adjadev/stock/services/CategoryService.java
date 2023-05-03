package com.adjadev.stock.services;

import com.adjadev.stock.dto.CategoryDto;
import com.adjadev.stock.dto.FournisseurDto;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer idCategory);
    CategoryDto findCategoryByCode(String code);

    List<CategoryDto> findAll();

    void delete(Integer idCategory);

}
