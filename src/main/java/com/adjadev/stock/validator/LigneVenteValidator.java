package com.adjadev.stock.validator;

import com.adjadev.stock.dto.LigneVenteDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {
    public static List<String> validate(LigneVenteDto ligneVenteDto){
        List<String> errors = new ArrayList<>();
        if(ligneVenteDto ==null){
            errors.add("Veuillez renseigner la quantité de la ligne de vente");
            errors.add("Veuillez renseigner le prix unitaire de la ligne de vente");
            errors.addAll(VentesValidator.validate(null));
            return errors;
        }
        if(ligneVenteDto.getQuantity() ==null || ligneVenteDto.getQuantity().compareTo(BigDecimal.ZERO) ==0){
            errors.add("Veuillez renseigner la quantité de la ligne de vente");
        }
        if(ligneVenteDto.getPrixUnitaire() ==null || ligneVenteDto.getPrixUnitaire().compareTo(BigDecimal.ZERO) ==0){
            errors.add("Veuillez renseigner le prix unitaire de la ligne de vente");
        }
        errors.addAll(VentesValidator.validate(ligneVenteDto.getVente()));
        return errors;
    }
}
