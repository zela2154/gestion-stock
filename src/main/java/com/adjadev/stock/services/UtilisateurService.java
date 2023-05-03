package com.adjadev.stock.services;

import com.adjadev.stock.dto.ChangerMotDePasseUtilisateurDto;
import com.adjadev.stock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer idUtilisateur);

    List<UtilisateurDto> findAll();

    void delete(Integer idUtilisateur);
    UtilisateurDto findByEmail(String email);

    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
