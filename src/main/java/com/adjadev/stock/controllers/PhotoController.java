package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.PhotoApi;
import com.adjadev.stock.services.strategy.StrategyPhotoContext;
import com.flickr4java.flickr.FlickrException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController implements PhotoApi {
     private StrategyPhotoContext strategyPhotoContext;
    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }
}
