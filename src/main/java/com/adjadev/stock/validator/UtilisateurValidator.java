package com.adjadev.stock.validator;

import com.adjadev.stock.dto.AdresseDto;
import com.adjadev.stock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {
    public static List<String> validate(UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();
        if(utilisateurDto ==null){
            errors.add("Veuillez renseigner le nom d'utilisateur");
            errors.add("Veuillez renseigner le prénom d'utilisateur");
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }
        if(!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("Veuillez renseigner le nom de l'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("Veuillez renseigner le prénom de l'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getMotPasse())){
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veuillez renseigner l'email de l'utilisateur");
        }
        if(utilisateurDto.getDateNaissance() == null){
            errors.add("Veuillez renseigner la date de naissance de l'utilisateur");
        }
        errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));
        return errors;
    }
}
