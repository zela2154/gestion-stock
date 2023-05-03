package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.*;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.LigneCommandeClient;
import com.adjadev.stock.model.LigneCommandeFournisseur;
import com.adjadev.stock.model.LigneVente;
import com.adjadev.stock.repository.*;
import com.adjadev.stock.services.ArticleService;
import com.adjadev.stock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneCommandeClientRepository commandeClientRepository;
    private LigneCommandeFournisseurRepository commandeFournisseurRepository;
    private

    @Autowired
    ArticleServiceImpl(
            ArticleRepository articleRepository,
            VentesRepository ventesRepository,
            LigneCommandeClientRepository commandeClientRepository,
            LigneCommandeFournisseurRepository commandeFournisseurRepository
    ){
     this.articleRepository =articleRepository;
     this.ventesRepository =ventesRepository;
     this.commandeClientRepository =commandeClientRepository;
     this.commandeFournisseurRepository = commandeFournisseurRepository;
    }
    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if(errors.isEmpty()){
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto)
                )
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return null;
        }
        return articleRepository.findById(id).map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID "+id+" n'a été trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                ));
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)){
            log.error("Article Code is null");
            return null;
        }
        return articleRepository.findByCodeArticle(codeArticle).map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec le code "+ codeArticle+" n'a été trouvé dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                )
                );
    }

    @Override
    public ArticleDto findArticleByCategoryId(Integer idCategory) {
        return null;
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(Integer idArticle) {
        return ventesRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFourniseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
       if(id ==null){
           log.error("Article ID is null");
       }
       List<LigneCommandeClient> ligneCommandeClients =commandeClientRepository.findAllByArticleId(id);
       if(!ligneCommandeClients.isEmpty()){
           throw new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans des" +
                   "commandes clients", ErrorCodes.ARTICLE_ALREADY_IN_USE);
       }
       List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
       if(!ligneCommandeFournisseurs.isEmpty()){
           throw new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans des" +
                   "commandes fournisseurs", ErrorCodes.ARTICLE_ALREADY_IN_USE);
       }
       List<LigneVente> ligneVentes = ventesRepository.findAllByArticleId(id);
       if(!ligneVentes.isEmpty()){
           throw new InvalidOperationException("Impossible de supprimer un article déjà utilisé dans des" +
                   "ventes", ErrorCodes.ARTICLE_ALREADY_IN_USE);
       }
       articleRepository.deleteById(id);
    }
}
