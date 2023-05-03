package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.CommandeFournisseurApi;
import com.adjadev.stock.dto.CommandeFournisseurDto;
import com.adjadev.stock.dto.LigneCommandeFournisseurDto;
import com.adjadev.stock.model.EtatCommande;
import com.adjadev.stock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {
    private CommandeFournisseurService service;
    @Autowired
    CommandeFournisseurController(CommandeFournisseurService service){
        this.service = service;
    }
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        return service.save(dto);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return service.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return service.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return service.updateFournisseur(idCommande, idFournisseur);
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return service.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return service.deleteArticle(idCommande, idLigneCommande);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        return service.findById(id);
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        return service.findByCode(code);
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return service.findAll();
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return service.findAllLignesCommandesFournisseurByCommandeFournisseurId(idCommande);
    }

    @Override
    public void delete(Integer id) {
      service.delete(id);
    }
}
