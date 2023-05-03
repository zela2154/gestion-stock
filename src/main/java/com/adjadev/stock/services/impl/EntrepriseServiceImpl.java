package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.EntrepriseDto;
import com.adjadev.stock.dto.RolesDto;
import com.adjadev.stock.dto.UtilisateurDto;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.repository.EntrepriseRepository;
import com.adjadev.stock.repository.RolesRepository;
import com.adjadev.stock.services.EntrepriseService;
import com.adjadev.stock.services.UtilisateurService;
import com.adjadev.stock.validator.EntrepriseValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {
    EntrepriseRepository entrepriseRepository;
    RolesRepository rolesRepository;
    UtilisateurService utilisateurService;
    @Autowired
    EntrepriseServiceImpl(
            EntrepriseRepository entrepriseRepository,
            RolesRepository rolesRepository,
            UtilisateurService utilisateurService
    ){
        this.entrepriseRepository =entrepriseRepository;
        this.rolesRepository=rolesRepository;
        this.utilisateurService=utilisateurService;
    }
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Entreprise is not valid {}", dto);
            throw new InvalidEntityException("L'entreprise n'est pas valide",
                    ErrorCodes.ENTREPRISE_NOT_VALID,errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(
                        EntrepriseDto.toEntity(dto)
                )
        );
        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);
        UtilisateurDto savedUser= utilisateurService.save(utilisateur);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();
        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    @Override
    public EntrepriseDto findById(Integer idEntreprise) {
        if(idEntreprise == null){
            log.error("Entreprise ID is NULL");
            return null;
        }
        return entrepriseRepository.findById(idEntreprise)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'id "+idEntreprise+
                                " n'a été trouvée",ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idEntreprise) {
        if (idEntreprise == null){
            log.error("Entreprise id is NULL");
        }
        entrepriseRepository.deleteById(idEntreprise);
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto dto){
        return UtilisateurDto.builder()
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .adresse(dto.getAdresse())
                .email(dto.getEmail())
                .motPasse(generateRandomPassword())
                .entreprise(dto)
                .photo(dto.getPhoto())
                .dateNaissance(Instant.now())
                .build();
    }
    private String generateRandomPassword(){
        return "som3R@nd0mP@$$word";
    }
}
