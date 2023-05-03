package com.adjadev.stock.services;

import com.adjadev.stock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer idEntreprise);

    List<EntrepriseDto> findAll();

    void delete(Integer idEntreprise);
}
