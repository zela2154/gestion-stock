package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.ArticleDto;
import com.adjadev.stock.dto.CategoryDto;
import com.adjadev.stock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adjadev.stock.utils.Constants.APP_ROOT;

@Api("clients")
public interface ClientApi {
    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Enregistrer un client", notes = "Cette méthode permet d'enregistrer ou modifier " +
            "un client", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet client crée / modifier"),
            @ApiResponse(code = 400, message = "L'objet client n'est pas valide")
    })
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(value = APP_ROOT + "/clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Rechercher un client par ID", notes = "Cette méthode de rechercher client par ID " , response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun client n'existe dans la BDD avec l'ID fourni")
    })
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des clients", notes = "Cette méthode permet de lister les clients",
            responseContainer ="List<ClientDto>" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des clients /Une liste vide"),
    })
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    @ApiOperation(value = "Supprimer un client", notes = "Cette méthode permet de supprimer un client par ID",
            response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été supprimé")
    })
    void delete(@PathVariable("idClient") Integer id);
}
