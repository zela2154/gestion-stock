package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.ArticleApi;
import com.adjadev.stock.dto.ArticleDto;
import com.adjadev.stock.dto.LigneCommandeClientDto;
import com.adjadev.stock.dto.LigneCommandeFournisseurDto;
import com.adjadev.stock.dto.LigneVenteDto;
import com.adjadev.stock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ArticleController implements ArticleApi {
    private ArticleService articleService;
    @Autowired
    ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }
    @Override
    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public ArticleDto findArticleByCategoryId(Integer idCategory) {
        return articleService.findArticleByCategoryId(idCategory);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(Integer idArticle) {
        return articleService.findHistoriqueVente(idArticle);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return articleService.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFourniseur(Integer idArticle) {
        return articleService.findHistoriqueCommandeFourniseur(idArticle);
    }

    @Override
    public void delete(Integer id) {
      articleService.delete(id);
    }
}
