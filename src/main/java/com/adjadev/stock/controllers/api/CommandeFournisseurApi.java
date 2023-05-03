package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.CommandeFournisseurDto;
import com.adjadev.stock.dto.LigneCommandeFournisseurDto;
import com.adjadev.stock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.adjadev.stock.utils.Constants.*;

@Api("commandesfournisseurs")
public interface CommandeFournisseurApi {

        @PostMapping(value = CREATE_COMMANDE_FOURNISSEUR_ENDPOINT,
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

        @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                                  @PathVariable("etatCommande") EtatCommande etatCommande);

        @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                      @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                      @PathVariable("quantite") BigDecimal quantite);

        @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/fournisseur/{idCommande}/{idFournisseur}",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande") Integer idCommande,
                                                 @PathVariable("idFournisseur") Integer idFournisseur);

        @PatchMapping(value = COMMANDE_FOURNISSEUR_ENDPOINT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto updateArticle(@PathVariable("idCommande") Integer idCommande,
                                             @PathVariable("idLigneCommande") Integer idLigneCommande,
                                             @PathVariable("idArticle") Integer idArticle);

        @DeleteMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
        CommandeFournisseurDto deleteArticle(@PathVariable("idCommande") Integer idCommande,
                                             @PathVariable("idLigneCommande") Integer idLigneCommande);

        @GetMapping(value = FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

        @GetMapping(value = FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
        CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

        @GetMapping(value = FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
        List<CommandeFournisseurDto> findAll();

        @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/lignesCommande/{idCommande}")
        List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

        @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
        void delete(@PathVariable("idCommandeFournisseur") Integer id);

    }

