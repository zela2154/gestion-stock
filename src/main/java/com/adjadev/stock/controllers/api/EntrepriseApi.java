package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.ClientDto;
import com.adjadev.stock.dto.EntrepriseDto;
import com.adjadev.stock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adjadev.stock.utils.Constants.ENTREPRISE_ENDPOINT;

@Api("entreprises")
public interface EntrepriseApi {
    @PostMapping(value = ENTREPRISE_ENDPOINT + "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Créer une entreprise", notes = "Cette méthode permet d'enregistrer ou modifier une entreprise",
    response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet entreprise créé / modifié"),
            @ApiResponse(code = 400, message = "L'objet entreprise n'est pas valide")
    })
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(value = ENTREPRISE_ENDPOINT + "/{idEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Rechercher une entreprise par ID", notes = "Cette méthode de rechercher un entreprise par ID " ,
            response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entreprise a été trouvée dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune entreprise n'existe dans la BDD avec l'ID fourni")
    })
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(value = ENTREPRISE_ENDPOINT + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des entreprises", notes = "Cette méthode permet de lister les entreprises",
            responseContainer ="List<EntrepriseDto>" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des entreprises /Une liste vide"),
    })
    List<EntrepriseDto> findAll();

    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}")
    @ApiOperation(value = "Supprimer une entreprise", notes = "Cette méthode permet de supprimer une entreprise par ID",
            response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'entreprise a été supprimée")
    })
    void delete(@PathVariable("idEntreprise") Integer id);
}
