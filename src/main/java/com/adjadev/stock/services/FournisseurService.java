package com.adjadev.stock.services;

import com.adjadev.stock.dto.FournisseurDto;

import java.util.List;

public interface FournisseurService {
    FournisseurDto save(FournisseurDto dto);

    FournisseurDto findById(Integer idFournisseur);

    List<FournisseurDto> findAll();

    void delete(Integer idFournisseur);
}
