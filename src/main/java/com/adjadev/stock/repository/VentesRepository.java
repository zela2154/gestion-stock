package com.adjadev.stock.repository;

import com.adjadev.stock.model.LigneVente;
import com.adjadev.stock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VentesRepository extends JpaRepository<Ventes,Integer> {
    Optional<Ventes> findVentesByCode(String code);
    List<LigneVente> findAllByArticleId(Integer idArticle);

    List<LigneVente> findAllByVenteId(Integer idVente);
}
