package com.adjadev.stock.validator;

import com.adjadev.stock.dto.AdresseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {
    public static List<String> validate(AdresseDto adresseDto){
        List<String> errors = new ArrayList<>();
        if(adresseDto ==null){
            errors.add("Veuillez renseigner l'adresse1");
            errors.add("Veuillez renseigner  l'adresse2");
            errors.add("Veuillez renseigner le pays");
            errors.add("Veuillez renseigner le code postale");
        }
        if(!StringUtils.hasLength(adresseDto.getAdresse1())){
            errors.add("Veuillez renseigner l'adresse1");
        }
        if(adresseDto == null || !StringUtils.hasLength(adresseDto.getAdresse2())){
            errors.add("Veuillez renseigner  l'adresse2");
        }
        if(!StringUtils.hasLength(adresseDto.getPays())){
            errors.add("Veuillez renseigner le pays");
        }
        if(!StringUtils.hasLength(adresseDto.getCodePostale())){
            errors.add("Veuillez renseigner le code postale");
        }
        return errors;
    }
}
