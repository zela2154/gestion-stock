package com.adjadev.stock.services.strategy;

import com.adjadev.stock.dto.ArticleDto;
import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidOperationException;
import com.adjadev.stock.services.ArticleService;
import com.adjadev.stock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {
    private FlickrService flickrService;
    private ArticleService articleService;
    @Autowired
    SaveArticlePhoto(FlickrService flickrService, ArticleService articleService){
        this.flickrService = flickrService;
        this.articleService = articleService;
    }
    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String title) throws FlickrException {
        ArticleDto article = articleService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, title);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        article.setPhoto(urlPhoto);
        return articleService.save(article);
    }
}
