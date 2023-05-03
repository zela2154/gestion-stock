package com.adjadev.stock.validator;

import com.adjadev.stock.dto.LigneCommandeFournisseurDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {
    public static List<String> validate(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){
        List<String> errors = new ArrayList<>();
        if(ligneCommandeFournisseurDto ==null){
            errors.add("Veuillez renseigner la quantité de la ligne de commande du client");
            errors.add("Veuillez renseigner le prix unitaire de la ligne de commande du client");
            errors.addAll(ArticleValidator.validate(null));
            errors.addAll(CommandeClientValidator.validate(null));
        }
        if(ligneCommandeFournisseurDto.getQuantity() ==null
                || ligneCommandeFournisseurDto.getQuantity().compareTo(BigDecimal.ZERO) ==0){
            errors.add("Veuillez renseigner la quantité de la ligne de commande du client");

        }
        if(ligneCommandeFournisseurDto.getPrixUnitaire() ==null
                || ligneCommandeFournisseurDto.getPrixUnitaire().compareTo(BigDecimal.ZERO) ==0){
            errors.add("Veuillez renseigner le prix unitaire de la ligne de commande du client");

        }
        errors.addAll(ArticleValidator.validate(ligneCommandeFournisseurDto.getArticle()));
        errors.addAll(CommandeFournisseurValidator.validate(ligneCommandeFournisseurDto.getCommandeFournisseur()));
        return errors;
    }
}
