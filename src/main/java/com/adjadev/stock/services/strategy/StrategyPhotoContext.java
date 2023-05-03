package com.adjadev.stock.services.strategy;

import com.adjadev.stock.exception.ErrorCodes;
import com.adjadev.stock.exception.InvalidOperationException;
import com.flickr4java.flickr.FlickrException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {
  private BeanFactory beanFactory;
  private Strategy strategy;
  @Setter
  private String context;
  @Autowired
    StrategyPhotoContext(BeanFactory beanFactory){
      this.beanFactory = beanFactory;
  }

  public Object savePhoto(String context, Integer id, InputStream photo, String title) throws FlickrException{
      determineContext(context);
      return strategy.savePhoto(id, photo, title);
  }

  private void determineContext(String context) {
      final String beanName = context + "Strategy";
      switch (context) {
          case "article":
              strategy = beanFactory.getBean(beanName, SaveArticlePhoto.class);
              break;
          case "client":
              strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
              break;
          case "fournisseur":
              strategy = beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
              break;
          case "entreprise":
              strategy = beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
              break;
          case "utilisateur":
              strategy = beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
              break;
          default:
              throw new InvalidOperationException("Context inconnu pour l'enregistrement de la photo",
                      ErrorCodes.UNKNOWN_CONTEXT);
      }
  }
}
