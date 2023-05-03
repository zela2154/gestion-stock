package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.EntrepriseApi;
import com.adjadev.stock.dto.EntrepriseDto;
import com.adjadev.stock.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntrepriseApi {
    private EntrepriseService service;
    @Autowired
    EntrepriseController(EntrepriseService service){
        this.service = service;
    }
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return service.save(dto);
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        return service.findById(id);
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return service.findAll();
    }

    @Override
    public void delete(Integer id) {
            service.delete(id);
    }
}
