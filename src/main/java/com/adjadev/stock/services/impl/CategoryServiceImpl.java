package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.CategoryDto;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.Article;
import com.adjadev.stock.repository.ArticleRepository;
import com.adjadev.stock.repository.CategoryRepository;
import com.adjadev.stock.services.CategoryService;
import com.adjadev.stock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    ArticleRepository articleRepository;

    @Autowired
    CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ArticleRepository articleRepository
    ){
      this.categoryRepository = categoryRepository;
      this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Category is not valid {}", dto);
            throw new InvalidEntityException("La catégorie n'est pas valide",
                    ErrorCodes.CATEGORY_NOT_VALID);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(dto)
                )
        );
    }

    @Override
    public CategoryDto findById(Integer idCategory) {
        if(idCategory ==null){
            log.error("Category ID is NULL");
            return null;
        }
        return categoryRepository.findById(idCategory)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune catégorie n'a été trouvée avec l'ID "+ idCategory,
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));
    }

    @Override
    public CategoryDto findCategoryByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Category code is null");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune categorie n'a été trouvée avec le code "+ code, ErrorCodes.CATEGORY_NOT_FOUND
                ));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idCategory) {
       if(idCategory ==null){
           log.error("Article ID is NULL");
       }
       List<Article> articles = articleRepository.findAllByCategoryId(idCategory);
       if(!articles.isEmpty()){
           throw new InvalidOperationException("Impossible de supprimer une catégorie qui est déjà utilisée",
                   ErrorCodes.CATEGORY_ALREADY_IN_USE);
       }
       categoryRepository.deleteById(idCategory);
    }
}
