package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.VentesApi;
import com.adjadev.stock.dto.VentesDto;
import com.adjadev.stock.services.VentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VentesController implements VentesApi {
    private VentesService service;
    @Autowired
    VentesController(VentesService service){
        this.service = service;
    }
    @Override
    public VentesDto save(VentesDto dto) {
        return service.save(dto);
    }

    @Override
    public VentesDto findById(Integer id) {
        return service.findById(id);
    }

    @Override
    public VentesDto findByCode(String code) {
        return service.findByCode(code);
    }

    @Override
    public List<VentesDto> findAll() {
        return service.findAll();
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }
}
