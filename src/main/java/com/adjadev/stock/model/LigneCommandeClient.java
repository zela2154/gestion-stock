package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandeclient")
public class LigneCommandeClient extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name ="idarticle")
    private Article article;
    @ManyToOne
    @JoinColumn(name ="idcommandeclient")
    private CommandeClient commandeClient;
    @Column(name ="quantity")
    private BigDecimal quantity;
    @Column(name ="prixunitaire")
    private BigDecimal prixUnitaire;
    @Column(name ="identreprise")
    private Integer idEntreprise;
}
