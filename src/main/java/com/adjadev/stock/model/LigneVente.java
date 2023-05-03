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
@Table(name = "lignevente")
public class LigneVente extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name ="idvente")
    private Ventes vente;
    @Column(name ="quantity")
    private BigDecimal quantity;
    @Column(name ="prixunitaire")
    private BigDecimal prixUnitaire;
    @Column(name ="identreprise")
    private Integer idEntreprise;
    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;
}
