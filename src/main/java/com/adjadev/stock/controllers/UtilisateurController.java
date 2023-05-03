package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.UtilisateurAPi;
import com.adjadev.stock.dto.ChangerMotDePasseUtilisateurDto;
import com.adjadev.stock.dto.UtilisateurDto;
import com.adjadev.stock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurController implements UtilisateurAPi {
    private UtilisateurService service;
    @Autowired
    UtilisateurController(UtilisateurService service){
        this.service = service;
    }
    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return service.save(dto);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        return service.changerMotDePasse(dto);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return service.findById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return service.findByEmail(email);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return service.findAll();
    }

    @Override
    public void delete(Integer id) {
         service.delete(id);
    }
}
