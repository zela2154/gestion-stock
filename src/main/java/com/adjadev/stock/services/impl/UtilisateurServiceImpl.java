package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.ChangerMotDePasseUtilisateurDto;
import com.adjadev.stock.dto.UtilisateurDto;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.Utilisateur;
import com.adjadev.stock.repository.UtilisateurRepository;
import com.adjadev.stock.services.UtilisateurService;
import com.adjadev.stock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    UtilisateurRepository utilisateurRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    UtilisateurServiceImpl(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder
    ){
       this.utilisateurRepository = utilisateurRepository;
       this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("User is not valid {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID,
                    errors);
        }
        if(userAlreadyExists(dto.getEmail())){
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe déjà",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));
        }
        dto.setMotPasse(passwordEncoder.encode(dto.getMotPasse()));

        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDto.toEntity(dto)
                )
        );
    }

    @Override
    public UtilisateurDto findById(Integer idUtilisateur) {
        if(idUtilisateur ==null){
            log.error("Utilisateur ID is NULL");
            return null;
        }
        return utilisateurRepository.findById(idUtilisateur)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID "+ idUtilisateur+" n'a été trouvé",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idUtilisateur) {
       if(idUtilisateur == null){
           log.error("User id is null");
       }
       utilisateurRepository.deleteById(idUtilisateur);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Utilisateur email is null");
            return null;
        }
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur n'a été trouvé avec l'email "+email,
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if(utilisateurOptional.isEmpty()){
            log.warn("Aucun utilisateur n'a été trouvé avec l'id "+dto.getId());
            throw new EntityNotFoundException("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId(), ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotPasse(passwordEncoder.encode(dto.getMotDePasse()));
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }

    private boolean userAlreadyExists(String email){
        Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByEmail(email);
        return user.isPresent();
    }

    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException("Aucune information n'a ete fourni pour pouvoir changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException("ID utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmerMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
            throw new InvalidOperationException("Mot de passe utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!dto.getMotDePasse().equals(dto.getConfirmerMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe different");
            throw new InvalidOperationException("Mots de passe utilisateur non conformes:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
