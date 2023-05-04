package com.adjadev.stock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
//@Builder
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Roles extends AbstractEntity{
    @Column(name ="rolename")
    private String roleName;
    @ManyToOne
    @JoinColumn(name ="idutilisateur")
    private Utilisateur utilisateur;
}
