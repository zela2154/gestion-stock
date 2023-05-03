package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.ClientApi;
import com.adjadev.stock.dto.ClientDto;
import com.adjadev.stock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ClientController implements ClientApi {

    private ClientService service;

    @Autowired
    ClientController(ClientService service){
        this.service = service;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return service.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return service.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return service.findAll();
    }

    @Override
    public void delete(Integer id) {
         service.delete(id);
    }
}
