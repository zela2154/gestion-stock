package com.adjadev.stock.services.impl;

import com.adjadev.stock.dto.MvtStockDto;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidEntityException;
import com.adjadev.stock.model.MvtStock;
import com.adjadev.stock.model.TypeMvtStock;
import com.adjadev.stock.repository.MvtStockRepository;
import com.adjadev.stock.services.ArticleService;
import com.adjadev.stock.services.MvtStockService;
import com.adjadev.stock.validator.MvtStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStockServiceImpl implements MvtStockService {
    private MvtStockRepository mvtStockRepository;
    private ArticleService articleService;
    @Autowired
    MvtStockServiceImpl(MvtStockRepository mvtStockRepository,ArticleService articleService){
        this.mvtStockRepository = mvtStockRepository;
        this.articleService = articleService;
    }
    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if (idArticle ==null){
            log.warn("ID article is null");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(idArticle);
        return mvtStockRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStockDto> mvtStockArticle(Integer idArticle) {
        return mvtStockRepository.findAllByArticleId(idArticle).stream()
                .map(MvtStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStockDto entreeStock(MvtStockDto mvtStockDto) {
        return entreePositive(mvtStockDto, TypeMvtStock.ENTREE);
    }

    @Override
    public MvtStockDto sortieStock(MvtStockDto mvtStockDto) {
        return sortieNegative(mvtStockDto, TypeMvtStock.SORTIE);
    }

    @Override
    public MvtStockDto correctionStockPos(MvtStockDto mvtStockDto) {
        return entreePositive(mvtStockDto, TypeMvtStock.CORRECTION_POS);
    }

    @Override
    public MvtStockDto correctionStockNeg(MvtStockDto mvtStockDto) {
        return sortieNegative(mvtStockDto, TypeMvtStock.CORRECTION_NEG);
    }

    private MvtStockDto entreePositive(MvtStockDto mvtStockDto, TypeMvtStock typeMvtStock){
        List<String> errors = MvtStockValidator.validate(mvtStockDto);
        if(!errors.isEmpty()){
            log.error("Article is not valid {}",mvtStockDto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valid",
                    ErrorCodes.MVT_STOCK_NOT_VALID, errors);
        }
        mvtStockDto.setQuantity(
                BigDecimal.valueOf(
                        Math.abs(mvtStockDto.getQuantity().doubleValue())
                )
        );
        mvtStockDto.setTypeMvtStock(typeMvtStock);
        return MvtStockDto.fromEntity(
                mvtStockRepository.save(MvtStockDto.toEntity(mvtStockDto))
        );
    }
    private MvtStockDto sortieNegative(MvtStockDto dto, TypeMvtStock typeMvtStk) {
        List<String> errors = MvtStockValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STOCK_NOT_VALID, errors);
        }
        dto.setQuantity(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantity().doubleValue()) * -1
                )
        );
        dto.setTypeMvtStock(typeMvtStk);
        return MvtStockDto.fromEntity(
                mvtStockRepository.save(MvtStockDto.toEntity(dto))
        );
    }
}
