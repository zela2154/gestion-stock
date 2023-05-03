package com.adjadev.stock.services;

import com.adjadev.stock.dto.CommandeClientDto;
import com.adjadev.stock.dto.CommandeFournisseurDto;
import com.adjadev.stock.dto.LigneCommandeClientDto;
import com.adjadev.stock.dto.LigneCommandeFournisseurDto;
import com.adjadev.stock.model.CommandeFournisseur;
import com.adjadev.stock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDto save(CommandeFournisseurDto dto);
    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);
    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idClient);

    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer newIdArticle);

    // Delete article ==> delete LigneCommandeFournisseur
    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    void delete(Integer id);
}
