package com.adjadev.stock.dto;

import com.adjadev.stock.model.Article;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ArticleDto {
    private Integer id;
    private String codeArticle;
    private String designation;
    private BigDecimal prixUnitaireHt;
    private BigDecimal tauxTva;
    private BigDecimal prixUnitaireTtc;
    private String photo;
    private CategoryDto category;
    private Integer idEntreprise;

    public static ArticleDto fromEntity(Article article){
        if(article == null){
            return null;
        }
        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .tauxTva(article.getTauxTva())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .photo(article.getPhoto())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .idEntreprise(article.getIdEntreprise())
                .build();
    }

    public static  Article toEntity(ArticleDto articleDto){
        if(articleDto ==null){
            return  null;
        }
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setTauxTva(articleDto.getTauxTva());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setPhoto(articleDto.getPhoto());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
        return article;
    }
}
