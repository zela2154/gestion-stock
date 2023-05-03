package com.adjadev.stock.validator;

import com.adjadev.stock.dto.LigneCommandeClientDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeClientValidator {
    public static List<String> validate(LigneCommandeClientDto ligneCommandeClientDto){
        List<String> errors = new ArrayList<>();
        if(ligneCommandeClientDto ==null){
          errors.add("Veuillez renseigner la quantité de la ligne de commande du client");
          errors.add("Veuillez renseigner le prix unitaire de la ligne de commande du client");
          errors.addAll(ArticleValidator.validate(null));
          errors.addAll(CommandeClientValidator.validate(null));
        }
        if(ligneCommandeClientDto.getQuantity() ==null
                || ligneCommandeClientDto.getQuantity().compareTo(BigDecimal.ZERO) ==0){
            errors.add("Veuillez renseigner la quantité de la ligne de commande du client");

        }
        if(ligneCommandeClientDto.getPrixUnitaire() ==null
                || ligneCommandeClientDto.getPrixUnitaire().compareTo(BigDecimal.ZERO) ==0){
            errors.add("Veuillez renseigner le prix unitaire de la ligne de commande du client");

        }
        errors.addAll(ArticleValidator.validate(ligneCommandeClientDto.getArticle()));
        errors.addAll(CommandeClientValidator.validate(ligneCommandeClientDto.getCommandeClient()));
        return errors;
    }
}
