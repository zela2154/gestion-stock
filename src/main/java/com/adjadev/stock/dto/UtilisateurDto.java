package com.adjadev.stock.dto;

import com.adjadev.stock.model.Role;
import com.adjadev.stock.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateurDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private Instant dateNaissance;
    private String motPasse;
    private AdresseDto adresse;
    private String photo;
    private EntrepriseDto entreprise;
    private Role role;
    @JsonIgnore
    private List<RolesDto> roles;
    private static  PasswordEncoder passwordEncoder;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                //.motPasse(utilisateur.getMotPasse())
                .motPasse(passwordEncoder.encode(utilisateur.getMotPasse()))
                .dateNaissance(utilisateur.getDateNaissance())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .role(utilisateur.getRole())
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        //utilisateur.setMotPasse(utilisateurDto.getMotPasse());
        utilisateur.setMotPasse(passwordEncoder.encode(utilisateurDto.getMotPasse()));
        utilisateur.setDateNaissance(utilisateurDto.getDateNaissance());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        utilisateur.setRole(utilisateurDto.getRole());
        return utilisateur;
    }
}
