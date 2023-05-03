package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.FournisseurApi;
import com.adjadev.stock.dto.FournisseurDto;
import com.adjadev.stock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FournisseurController implements FournisseurApi {
    private FournisseurService service;
    @Autowired
    FournisseurController(FournisseurService service){
        this.service =service;
    }
    @Override
    public FournisseurDto save(FournisseurDto dto) {
        return service.save(dto);
    }

    @Override
    public FournisseurDto findById(Integer id) {
        return service.findById(id);
    }

    @Override
    public List<FournisseurDto> findAll() {
        return service.findAll();
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }
}
