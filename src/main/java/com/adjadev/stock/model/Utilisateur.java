package com.adjadev.stock.model;

import com.adjadev.stock.token.Token;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity implements UserDetails {
    @Column(name ="nom")
    private String nom;
    @Column(name ="prenom")
    private String prenom;
    @Column(name = "email")
    private String email;
    @Column(name ="datenaissance")
    private Instant dateNaissance;
    @Column(name ="motpasse")
    private String motPasse;
    @Embedded
    private Adresse adresse;
    @Column(name ="photo")
    private String photo;
    @ManyToOne
    @JoinColumn(name ="identreprise")
    private Entreprise entreprise;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utilisateur")
    private List<Roles> roles;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return motPasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
