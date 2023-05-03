package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.*;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.*;
import com.adjadev.stock.repository.*;
import com.adjadev.stock.services.CommandeClientService;
import com.adjadev.stock.services.MvtStockService;
import com.adjadev.stock.validator.ArticleValidator;
import com.adjadev.stock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {
    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;
    private MvtStockService mvtStockService;
    @Autowired
    CommandeClientServiceImpl(
            CommandeClientRepository commandeClientRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository,
            ClientRepository clientRepository,
            ArticleRepository articleRepository,
            MvtStockService mvtStockService
    ){
      this.commandeClientRepository = commandeClientRepository;
      this.ligneCommandeClientRepository = ligneCommandeClientRepository;
      this.clientRepository = clientRepository;
      this.articleRepository = articleRepository;
      this.mvtStockService = mvtStockService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
       List<String> errors = CommandeClientValidator.validate(dto);
       if(!errors.isEmpty()){
           log.error("Commande client n'est pas valide");
           throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID);
       }
       if(dto.getId() !=null && dto.isCommandeLivree()){
         throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livrée",
                 ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
       }
        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
       if(client.isEmpty()){
           log.warn("Client with ID {} is not found in the DB", dto.getClient().getId());
           throw new EntityNotFoundException("Aucun client avec l'ID "+dto.getClient().getId()
                   +"n'a été trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND);
       }
       List<String> articleErrors = new ArrayList<>();
       if(dto.getLigneCommandeClients() !=null){
           dto.getLigneCommandeClients().forEach(ligCmdClt ->{
               if(ligCmdClt.getArticle() !=null){
                   Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                   if(article.isEmpty()){
                       articleErrors.add("L'article avec l'ID "+ligCmdClt.getArticle().getId()+" n'existe pas");
                   }
               }else{
                   articleErrors.add("Impossible d'enregistrer une commande avec un article null");
               }
           });
       }
       if(!articleErrors.isEmpty()){
           log.warn("");
           throw new InvalidEntityException("L'article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
       }
       dto.setDateCommande(Instant.now());
        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        if(dto.getLigneCommandeClients() !=null){
            dto.getLigneCommandeClients().forEach(ligCmdClt ->{
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClient(savedCmdClt);
                ligneCommandeClient.setIdEntreprise(dto.getIdEntreprise());

                LigneCommandeClient savedLigneCmd = ligneCommandeClientRepository.save(ligneCommandeClient);
                effectuerSortie(savedLigneCmd);
            });
        }
        return CommandeClientDto.fromEntity(savedCmdClt);
    }


    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        commandeClient.setEtatCommande(etatCommande);

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
        if (commandeClient.isCommandeLivree()) {
            updateMvtStock(idCommande);
        }

        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
       checkIdCommande(idCommande);
       checkIdLigneCommande(idLigneCommande);
       if(quantite == null || quantite.compareTo(BigDecimal.ZERO) ==0){
           log.error("L'id de la ligne de commande est null");
           throw new InvalidOperationException("Impossible de modifier l'état de la commande avec une" +
                   "quantité null ou Zero", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
       }
       CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);
       Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

       LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
       ligneCommandeClient.setQuantity(quantite);
       ligneCommandeClientRepository.save(ligneCommandeClient);
        return commandeClientDto;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient == null){
            log.error("L'ID du client est null");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec un ID client" +
                    "Null", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if(clientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client n'a été trouvé avec l'ID "+idClient,
                    ErrorCodes.CLIENT_NOT_FOUND);
        }
        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));
        return CommandeClientDto.fromEntity(
             commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer newIdArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(newIdArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);
        Optional<Article> articleOptional = articleRepository.findById(newIdArticle);
        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article n'a été trouvé avec l'ID "+newIdArticle,
                    ErrorCodes.ARTICLE_NOT_FOUND);
        }
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if(!errors.isEmpty()){
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClientOptional.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);
        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);
        return commandeClient;
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null){
            log.error("Commande client ID is NULL");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a été trouvée avec l'ID "+ id,ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande client code is NULL");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a été trouvée avec le code "+ code,ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Commande client ID is null");
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);

        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande client déjà utilisée",
                    ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
        }
        commandeClientRepository.deleteById(id);
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande){
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommande);
        if(ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("" +
                    "Aucune ligne commande client n'a été trouvée avec l'ID "+idLigneCommande,
                    ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande){
        CommandeClientDto commandeClient = findById(idCommande);
        if(commandeClient.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livrée",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClient;
    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande ==null){
            log.error("Commande ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'état d'une commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }
    private void checkIdLigneCommande(Integer idLigneCommande){
        if(idLigneCommande ==null){
            log.error("L'ID de la ligne de commande est null");
            throw new InvalidOperationException("Impossible de modifier l'état d'une commande avec un Id de" +
                    "ligne de commande null", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }
    private void checkIdArticle(Integer idArticle, String msg){
        if(idArticle ==null){
            log.error("l'ID de "+msg+"is null");
            throw new InvalidOperationException("Impossible de modifier l'état d'une commande avec "+
                    msg+" ID article null", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }
    private void updateMvtStock(Integer idCommande){
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig ->{
            effectuerSortie(lig);
        });
    }
    private void effectuerSortie(LigneCommandeClient ligneCommandeClient){
        MvtStockDto mvtStockDto = MvtStockDto.builder()
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .dateMvt(Instant.now())
                .typeMvtStock(TypeMvtStock.SORTIE)
                .sourceMvtStock(SourceMvtStock.COMMANDE_CLIENT)
                .quantity(ligneCommandeClient.getQuantity())
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                .build();
        mvtStockService.sortieStock(mvtStockDto);
    }
}
