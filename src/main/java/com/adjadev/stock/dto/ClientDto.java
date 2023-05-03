package com.adjadev.stock.dto;

import com.adjadev.stock.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ClientDto {
    private Integer id;
    private String nom;
    private String prenom;
    private AdresseDto adresse;
    private String mail;
    private String numTel;
    private String photo;
    @JsonIgnore
    private List<CommandeClientDto> commandeClients;

    public static ClientDto fromEntity(Client client){
        if(client == null){
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .photo(client.getPhoto())
                .build();
    }

    public static Client toEntity(ClientDto clientDto){
        if(clientDto ==null){
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
        client.setMail(clientDto.getMail());
        client.setNumTel(clientDto.getNumTel());
        client.setPhoto(clientDto.getPhoto());

        return  client;

    }
}
