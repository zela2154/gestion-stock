package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.MvtStockDto;
import com.adjadev.stock.model.MvtStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.adjadev.stock.utils.Constants.APP_ROOT;

@Api("mvtstock")
public interface MvtStockApi {
    @GetMapping(value = APP_ROOT + "/mvtstk/stockreel/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un stock reel dans un mouvement de stock", notes = "Cette méthode permet de rechercher" +
            "un stock reel dans un mouvement de stock avec l'ID d'un article", response = MvtStockDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le stock reel du mouvement stock a été trouvé dans la BDD"),
            @ApiResponse(code = 404,message = "Aucun mouvement de stock existe dans la BDD avec l'ID fourni")
    })
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT + "/mvtstk/filter/article/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Filtrer un mouvement de stock d'un article", notes = "Cette méthode permet de filtrer" +
            "un mouvement de stock d'un article par son ID", responseContainer = "List<MvtStockDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "MvtStock trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun mvtstock trouvé avec l'ID fourni")
    })
    List<MvtStockDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(value = APP_ROOT + "/mvtstk/entree", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une entrée de mvtstock", notes = "Cette méthode permet d'enregistrer / modifier" +
            "l'entrée d'un mvtstock", response = MvtStockDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entrée du mvtstock enregistrer / modifier"),
            @ApiResponse(code = 400, message = "L'entrée n'est pas valide")
    })
    MvtStockDto entreeStock(@RequestBody MvtStockDto dto);

    @PostMapping(value = APP_ROOT + "/mvtstk/sortie", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une sortie de mvtstock", notes = "Cette méthode permet d'enregistrer / modifier" +
            "une sortie d'un mvtstock", response = MvtStockDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La sortie du mvtstock enregistrer / modifier"),
            @ApiResponse(code = 400, message = "La sortie n'est pas valide")
    })
    MvtStockDto sortieStock(@RequestBody MvtStockDto dto);

    @PostMapping(value = APP_ROOT + "/mvtstk/correctionpos", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une correstion positive d'un mvtstock", notes = "Cette méthode permet d'enregistrer / modifier" +
            "la correction positive d'un mvtstock", response = MvtStockDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La correction positive du mvtstock enregistrer / modifier"),
            @ApiResponse(code = 400, message = "La correction positive n'est pas valide")
    })
    MvtStockDto correctionStockPos(@RequestBody MvtStockDto dto);

    @PostMapping(value = APP_ROOT + "/mvtstk/correctionneg", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une correstion négative d'un mvtstock", notes = "Cette méthode permet d'enregistrer / modifier" +
            "la correction négative d'un mvtstock", response = MvtStockDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La correction négative du mvtstock enregistrer / modifier"),
            @ApiResponse(code = 400, message = "La correction négative n'est pas valide")
    })
    MvtStockDto correctionStockNeg(@RequestBody MvtStockDto dto);
}
