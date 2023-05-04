package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
//@Builder
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity{
    @Column(name ="nom")
    private String nom;
    @Column(name ="description")
    private String description;
    @Column(name = "adresse")
    private Adresse adresse;
    @Column(name ="codefiscal")
    private String codeFiscal;
    @Column(name = "photo")
    private String photo;
    @Column(name= "email")
    private String email;
    @Column(name = "numtel")
    private String numTel;
    @Column(name = "siteweb")
    private String siteWeb;
    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs;
}
