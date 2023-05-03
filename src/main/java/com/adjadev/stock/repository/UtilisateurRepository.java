package com.adjadev.stock.repository;

import com.adjadev.stock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findUtilisateurByEmail(String email);

    List<Utilisateur> findUtilisateurById(Integer idUtilisateur);
}
