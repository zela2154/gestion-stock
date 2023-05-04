package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
//@Builder
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandefournisseur")
public class LigneCommandeFournisseur extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name ="idarticle")
    private Article article;
    @ManyToOne
    @JoinColumn(name ="idcommandefournisseur")
    private CommandeFournisseur commandeFournisseur;
    @Column(name ="quantity")
    private BigDecimal quantity;
    @Column(name ="prixunitaire")
    private BigDecimal prixUnitaire;
    @Column(name ="identreprise")
    private Integer idEntreprise;
}
