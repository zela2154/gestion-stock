package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.FournisseurDto;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.CommandeFournisseur;
import com.adjadev.stock.repository.CommandeFournisseurRepository;
import com.adjadev.stock.repository.FournisseurRepository;
import com.adjadev.stock.services.FournisseurService;
import com.adjadev.stock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {
    FournisseurRepository fournisseurRepository;
    CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    FournisseurServiceImpl(
            FournisseurRepository fournisseurRepository,
            CommandeFournisseurRepository commandeFournisseurRepository
    ){
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("Fournisseur is not valid {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID,
                    errors);
        }
        return FournisseurDto.fromEntity(
                fournisseurRepository.save(
                        FournisseurDto.toEntity(dto)
                )
        );
    }

    @Override
    public FournisseurDto findById(Integer idFournisseur) {
        if(idFournisseur == null){
            log.error("Fournisseur ID is NULL");
            return null;
        }
        return fournisseurRepository.findById(idFournisseur)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur n'a été trouvé avec l'ID "+ idFournisseur,
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idFournisseur) {
         if(idFournisseur == null){
             log.error("Fournisseur ID is null");
         }
         List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(idFournisseur);
         if(!commandeFournisseurs.isEmpty()){
             throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a déjà des" +
                     "commandes", ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
         }
         fournisseurRepository.deleteById(idFournisseur);
    }
}
