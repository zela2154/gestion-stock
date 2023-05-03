package com.adjadev.stock.services;

import com.adjadev.stock.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto dto);

    ClientDto findById(Integer idClient);
    List<ClientDto> findAll();
    void delete(Integer idClient);
}
