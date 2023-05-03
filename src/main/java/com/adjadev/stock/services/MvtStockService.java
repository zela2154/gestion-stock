package com.adjadev.stock.services;

import com.adjadev.stock.dto.MvtStockDto;

import java.math.BigDecimal;
import java.util.List;

public interface MvtStockService {
    BigDecimal stockReelArticle(Integer idArticle);
    List<MvtStockDto> mvtStockArticle(Integer idArticle);
    MvtStockDto entreeStock(MvtStockDto mvtStockDto);
    MvtStockDto sortieStock(MvtStockDto mvtStockDto);
    MvtStockDto correctionStockPos(MvtStockDto mvtStockDto);
    MvtStockDto correctionStockNeg(MvtStockDto mvtStockDto);
}
