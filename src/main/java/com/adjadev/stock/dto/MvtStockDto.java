package com.adjadev.stock.dto;

import com.adjadev.stock.model.MvtStock;
import com.adjadev.stock.model.SourceMvtStock;
import com.adjadev.stock.model.TypeMvtStock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStockDto {
    private Integer id;
    private Instant dateMvt;
    private BigDecimal quantity;
    private ArticleDto article;
    private TypeMvtStock typeMvtStock;
    private SourceMvtStock sourceMvtStock;
    private Integer idEntreprise;

    public static MvtStockDto fromEntity(MvtStock mvtStock){
        if(mvtStock ==null){
            return null;
        }
        return MvtStockDto.builder()
                .id(mvtStock.getId())
                .dateMvt(mvtStock.getDateMvt())
                .quantity(mvtStock.getQuantity())
                .article(ArticleDto.fromEntity(mvtStock.getArticle()))
                .typeMvtStock(mvtStock.getTypeMvt())
                .sourceMvtStock(mvtStock.getSourceMvt())
                .idEntreprise(mvtStock.getIdEntreprise())
                .build();
    }

    public static MvtStock toEntity(MvtStockDto mvtStockDto){
        if(mvtStockDto ==null){
            return null;
        }
        MvtStock mvtStock = new MvtStock();
        mvtStock.setId(mvtStockDto.getId());
        mvtStock.setDateMvt(mvtStockDto.getDateMvt());
        mvtStock.setQuantity(mvtStockDto.getQuantity());
        mvtStock.setArticle(ArticleDto.toEntity(mvtStockDto.getArticle()));
        mvtStock.setTypeMvt(mvtStockDto.getTypeMvtStock());
        mvtStock.setSourceMvt(mvtStockDto.getSourceMvtStock());
        mvtStock.setIdEntreprise(mvtStockDto.getIdEntreprise());
        return mvtStock;
    }
}
