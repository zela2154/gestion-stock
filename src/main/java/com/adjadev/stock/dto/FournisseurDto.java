package com.adjadev.stock.dto;


import com.adjadev.stock.model.Fournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FournisseurDto {
    private Integer id;
    private String nom;
    private String prenom;
    private AdresseDto adresse;
    private String mail;
    private String numTel;
    private String photo;
    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;
    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if(fournisseur == null){
            return null;
        }
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getNumTel())
                .photo(fournisseur.getPhoto())
                .build();
    }

    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if(fournisseurDto ==null){
            return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setAdresse(AdresseDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setPhoto(fournisseurDto.getPhoto());

        return  fournisseur;

    }
}
