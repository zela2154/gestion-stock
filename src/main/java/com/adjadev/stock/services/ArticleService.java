package com.adjadev.stock.services;

import com.adjadev.stock.dto.ArticleDto;
import com.adjadev.stock.dto.LigneCommandeClientDto;
import com.adjadev.stock.dto.LigneCommandeFournisseurDto;
import com.adjadev.stock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto dto);
    ArticleDto findById(Integer id);
    ArticleDto findByCodeArticle(String codeArticle);
    ArticleDto findArticleByCategoryId(Integer idCategory);
    List<ArticleDto> findAll();
    List<LigneVenteDto> findHistoriqueVente(Integer idArticle);
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFourniseur(Integer idArticle);
    void delete(Integer id);
}
