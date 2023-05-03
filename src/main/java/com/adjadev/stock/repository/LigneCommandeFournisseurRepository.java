package com.adjadev.stock.repository;

import com.adjadev.stock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {
    List<LigneCommandeFournisseur> findAllByArticleId(Integer idArticle);

    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);
}
