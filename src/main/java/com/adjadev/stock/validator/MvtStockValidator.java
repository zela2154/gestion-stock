package com.adjadev.stock.validator;

import com.adjadev.stock.dto.MvtStockDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStockValidator {
    public static List<String> validate(MvtStockDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
            errors.add("Veuillez renseigner la quantite du mouvenent");
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner le type du mouvement");

            return errors;
        }
        if (dto.getDateMvt() == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
        }
        if (dto.getQuantity() == null || dto.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantite du mouvenent");
        }
        if (dto.getArticle() == null || dto.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article");
        }
        /*if (!StringUtils.hasLength(dto.getTypeMvt().name())) {
            errors.add("Veuillez renseigner le type du mouvement");
        }*/

        return errors;
    }
}
