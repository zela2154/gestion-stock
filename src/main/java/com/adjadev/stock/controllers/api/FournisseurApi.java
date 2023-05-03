package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.ClientDto;
import com.adjadev.stock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adjadev.stock.utils.Constants.APP_ROOT;

@Api("fournsseurs")
public interface FournisseurApi {
    @PostMapping(value = APP_ROOT + "/fournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Enregistrer un fournisseur", notes = "Cette méthode permet d'enregistrer ou modifier " +
            "un fournisseur", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet fournisseur crée / modifier"),
            @ApiResponse(code = 400, message = "L'objet fournisseur n'est pas valide")
    })
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(value = APP_ROOT + "/fournisseurs/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Rechercher un fournisseur par ID", notes = "Cette méthode de rechercher un fournisseur par ID " ,
            response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun fournisseur n'existe dans la BDD avec l'ID fourni")
    })
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(value = APP_ROOT + "/fournisseur/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des fournisseurs", notes = "Cette méthode permet de lister les fournisseurs",
            responseContainer ="List<FournisseurDto>" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des fournisseurs /Une liste vide"),
    })
    List<FournisseurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/fournisseurs/delete/{idFournisseur}")
    @ApiOperation(value = "Supprimer un fournisseur", notes = "Cette méthode permet de supprimer un fournisseur par ID",
            response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a été supprimé")
    })
    void delete(@PathVariable("idFournisseur") Integer id);
}
