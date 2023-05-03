package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandeclient")
public class CommandeClient extends AbstractEntity{
    @Column(name ="code")
    private String code;
    @Column(name ="datecommande")
    private Instant dateCommande;
    @Column(name ="identreprise")
    private Integer idEntreprise;
    @ManyToOne
    @JoinColumn(name ="idclient")
    private Client client;
    @OneToMany(mappedBy = "commandeClient")
    private List<LigneCommandeClient> ligneCommandeClients;
}
