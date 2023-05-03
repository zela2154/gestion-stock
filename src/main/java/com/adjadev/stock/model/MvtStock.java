package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mouvementstock")
public class MvtStock extends AbstractEntity{
    @Column(name ="datemvt")
    private Instant dateMvt;
    @Column(name ="quantity")
    private BigDecimal quantity;
    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;
    @Column(name = "typemvt")
    @Enumerated(EnumType.STRING)
    private TypeMvtStock typeMvt;
    @Column(name = "sourcemvt")
    @Enumerated(EnumType.STRING)
    private SourceMvtStock sourceMvt;
    @Column(name ="identreprise")
    private Integer idEntreprise;
}
