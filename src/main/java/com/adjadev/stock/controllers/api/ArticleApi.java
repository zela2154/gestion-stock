package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.ArticleDto;
import com.adjadev.stock.dto.LigneCommandeClientDto;
import com.adjadev.stock.dto.LigneCommandeFournisseurDto;
import com.adjadev.stock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import static com.adjadev.stock.utils.Constants.APP_ROOT;
@Api("articles")
public interface ArticleApi {
    @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Enregistrer une catégorie", notes = "Cette méthode permet d'enregistrer ou modifier " +
            "une catégorie", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet catégory crée / modifier"),
            @ApiResponse(code = 400, message = "L'objet category n'est pas valide")
    })
    ArticleDto save(@RequestBody ArticleDto dto);
    @GetMapping(value = APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette méthode permet de chercher un article par ID",
    response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);
    @GetMapping(value = APP_ROOT + "/articles/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par code", notes = "Cette méthode permet de chercher un article par CODE",
            response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);
    @GetMapping(value = APP_ROOT + "/articles/filter/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findArticleByCategoryId(@PathVariable("idCategory") Integer idCategory);
    @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des articles", notes = "Cette méthode permet de lister les articles",
            responseContainer ="List<ArticleDto>" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles /Une liste vide"),
    })
    List<ArticleDto> findAll();
    @GetMapping(value = APP_ROOT + "/articles/historique/ventes/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette méthode permet de chercher un article par ID",
            response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    List<LigneVenteDto> findHistoriqueVente(@PathVariable("idArticle") Integer idArticle);
    @GetMapping(value = APP_ROOT + "/articles/historique/commandeclient/{idArtilce}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle") Integer idArticle);
    @GetMapping(value = APP_ROOT + "/articles/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFourniseur(@PathVariable("idArticle") Integer idArticle);
    @DeleteMapping(value = APP_ROOT + "/article/delete/{idArticle}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette méthode permet de supprimer un article par ID",
    response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé")
    })
    void delete(@PathVariable("idArticle") Integer id);
}
