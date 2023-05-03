package com.adjadev.stock.controllers;

import com.adjadev.stock.controllers.api.CategoryApi;
import com.adjadev.stock.controllers.api.MvtStockApi;
import com.adjadev.stock.dto.CategoryDto;
import com.adjadev.stock.dto.MvtStockDto;
import com.adjadev.stock.services.CategoryService;
import com.adjadev.stock.services.MvtStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
@RestController
public class CategoryController implements CategoryApi {
    private CategoryService service;
    @Autowired
    CategoryController(CategoryService service){
        this.service = service;
    }
    @Override
    public CategoryDto save(CategoryDto dto) {
        return service.save(dto);
    }

    @Override
    public CategoryDto findById(Integer idCategory) {
        return service.findById(idCategory);
    }

    @Override
    public CategoryDto findByCode(String codeCategory) {
        return service.findCategoryByCode(codeCategory);
    }

    @Override
    public List<CategoryDto> findAll() {
        return service.findAll();
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }

    @RestController
    public static class MvtStockController implements MvtStockApi {
        private MvtStockService service;
        @Autowired
        MvtStockController(MvtStockService service){
            this.service = service;
        }
        @Override
        public BigDecimal stockReelArticle(Integer idArticle) {
            return service.stockReelArticle(idArticle);
        }

        @Override
        public List<MvtStockDto> mvtStkArticle(Integer idArticle) {
            return service.mvtStockArticle(idArticle);
        }

        @Override
        public MvtStockDto entreeStock(MvtStockDto dto) {
            return service.entreeStock(dto);
        }

        @Override
        public MvtStockDto sortieStock(MvtStockDto dto) {
            return service.sortieStock(dto);
        }

        @Override
        public MvtStockDto correctionStockPos(MvtStockDto dto) {
            return service.correctionStockPos(dto);
        }

        @Override
        public MvtStockDto correctionStockNeg(MvtStockDto dto) {
            return service.correctionStockNeg(dto);
        }
    }
}
