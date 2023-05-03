package com.adjadev.stock.validator;

import com.adjadev.stock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {
    public static List<String> validate(CategoryDto categoryDto){
        List<String> errors =new ArrayList<>();
        if(categoryDto ==null || !StringUtils.hasLength(categoryDto.getCode())){
            errors.add("Veuillez renseigner le code de la catégorie");
        }
        return errors;
    }
}
