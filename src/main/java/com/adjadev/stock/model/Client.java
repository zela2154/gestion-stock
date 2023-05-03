package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
public class Client extends  AbstractEntity{
    @Column(name ="nom")
    private String nom;
    @Column(name ="prenom")
    private String prenom;
    @Embedded
    private Adresse adresse;
    @Column(name ="mail")
    private String mail;
    @Column(name ="numtel")
    private String numTel;
    @Column(name ="photo")
    private String photo;
    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients;
}