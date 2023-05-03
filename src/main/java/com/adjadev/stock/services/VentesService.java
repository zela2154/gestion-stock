package com.adjadev.stock.services;

import com.adjadev.stock.dto.VentesDto;

import java.util.List;

public interface VentesService {
    VentesDto save(VentesDto dto);

    VentesDto findById(Integer idVente);

    VentesDto findByCode(String code);

    List<VentesDto> findAll();

    void delete(Integer idVente);
}
