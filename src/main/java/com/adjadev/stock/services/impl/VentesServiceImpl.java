package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.ArticleDto;
import com.adjadev.stock.dto.LigneVenteDto;
import com.adjadev.stock.dto.MvtStockDto;
import com.adjadev.stock.dto.VentesDto;
import com.adjadev.stock.exception.EntityNotFoundException;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.model.*;
import com.adjadev.stock.repository.ArticleRepository;
import com.adjadev.stock.repository.LigneVenteRepository;
import com.adjadev.stock.repository.VentesRepository;
import com.adjadev.stock.services.MvtStockService;
import com.adjadev.stock.services.VentesService;
import com.adjadev.stock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    ArticleRepository articleRepository;
    VentesRepository ventesRepository;
    LigneVenteRepository ligneVenteRepository;
    MvtStockService mvtStockService;

    @Autowired
    VentesServiceImpl(
            ArticleRepository articleRepository,
            VentesRepository ventesRepository,
            LigneVenteRepository ligneVenteRepository,
            MvtStockService mvtStockService
    ){
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStockService = mvtStockService;
    }
    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VentesValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Ventes n'est pas valide {}", dto);
            throw new InvalidEntityException("L'objet vente n'est pas valide",
                    ErrorCodes.VENTE_NOT_VALID, errors);
        }
        List<String> articleErrors = new ArrayList<>();
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getVente().getId());
            if(article.isEmpty()){
                articleErrors.add("Aucun article avec l'ID "+ ligneVenteDto.getVente().getId()+
                        " n'a été trouvé");
            }
        });
        if(!articleErrors.isEmpty()){
            log.error("One or more articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas été trouvés dans la base de donnée",
                    ErrorCodes.VENTE_NOT_VALID,errors);
        }

        Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVentes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStock(ligneVente);
        });
        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findById(Integer idVente) {
        if(idVente == null){
            log.error("Vente ID is Null");
            return null;
        }
        return ventesRepository.findById(idVente)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente avec l'ID "+idVente+" n'a été trouvée",
                        ErrorCodes.VENTES_NOT_FOUND
                ));
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Vente code is null");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente n'a été trouvée avec le code "+ code, ErrorCodes.VENTES_NOT_FOUND
                ));
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idVente) {
        if(idVente == null){
            log.error("Vente ID is Null");
        }
        List<LigneVente> ligneVentes = ventesRepository.findAllByVenteId(idVente);
        if (!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une vente qui est déjà utilisé",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        ventesRepository.deleteById(idVente);
    }

    private void updateMvtStock(LigneVente lig) {
        MvtStockDto mvtStkDto = MvtStockDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvtStock(TypeMvtStock.SORTIE)
                .sourceMvtStock(SourceMvtStock.VENTE)
                .quantity(lig.getQuantity())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStockService.sortieStock(mvtStkDto);
    }
}
