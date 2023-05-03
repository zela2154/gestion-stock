package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.ClientDto;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.CommandeClient;
import com.adjadev.stock.repository.ClientRepository;
import com.adjadev.stock.repository.CommandeClientRepository;
import com.adjadev.stock.services.ClientService;
import com.adjadev.stock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    ClientRepository clientRepository;
    CommandeClientRepository commandeClientRepository;
    @Autowired
    ClientServiceImpl(
            ClientRepository clientRepository,
            CommandeClientRepository commandeClientRepository
    ){
       this.clientRepository = clientRepository;
       this.commandeClientRepository = commandeClientRepository;
    }
    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Client is not valid {}",dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(dto)
                )
        );
    }

    @Override
    public ClientDto findById(Integer idClient) {
        if(idClient == null){
            log.error("L'id du client is null");
            return null;
        }
        return clientRepository.findById(idClient)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun client avec l'id "+ idClient +
                "n'a été trouvé", ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idClient) {
        if(idClient == null){
            log.error("Client ID is Null");
        }
        List<CommandeClient> commandeClients = commandeClientRepository.finAllByClientId(idClient);
        if(!commandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un client qui a déjà des commandes",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        clientRepository.deleteById(idClient);
    }
}
