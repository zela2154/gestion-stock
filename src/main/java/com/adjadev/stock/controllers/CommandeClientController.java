package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.CommandeClientApi;
import com.adjadev.stock.dto.CommandeClientDto;
import com.adjadev.stock.dto.LigneCommandeClientDto;
import com.adjadev.stock.model.EtatCommande;
import com.adjadev.stock.services.CommandeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
@RestController
public class CommandeClientController implements CommandeClientApi {
    private CommandeClientService service;
    @Autowired
    CommandeClientController(CommandeClientService service){
        this.service = service;
    }
    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(service.updateEtatCommande(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(service.updateQuantiteCommande(idCommande, idLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateClient(Integer idCommande, Integer idClient) {
        return ResponseEntity.ok(service.updateClient(idCommande, idClient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return ResponseEntity.ok(service.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    @Override
    public ResponseEntity<CommandeClientDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {

        return ResponseEntity.ok(service.deleteArticle(idCommande, idLigneCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer idCommandeClient) {

        return ResponseEntity.ok(service.findById(idCommandeClient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findByCode(String code) {

        return ResponseEntity.ok(service.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ResponseEntity.ok(service.findAllLignesCommandesClientByCommandeClientId(idCommande));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
