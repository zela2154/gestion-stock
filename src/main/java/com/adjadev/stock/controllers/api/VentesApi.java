package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.UtilisateurDto;
import com.adjadev.stock.dto.VentesDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adjadev.stock.utils.Constants.VENTES_ENDPOINT;

@Api("ventes")
public interface VentesApi {
    @PostMapping(value = VENTES_ENDPOINT + "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer une vente", notes = "Cette méthode permet d'enregistrer/modifier une vente",
            response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet utilisateur créé / modifier"),
            @ApiResponse(code = 400, message = "L'objet utilisateur n'est pas valide")
    })
    VentesDto save(@RequestBody VentesDto dto);

    @GetMapping(value = VENTES_ENDPOINT + "/{idVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente", notes = "Cette méthode permet de rechercher" +
            "une vente par son ID", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vente trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune vente existe dans la BDD avec l'ID fourni")
    })
    VentesDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(value = VENTES_ENDPOINT + "/{codeVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente", notes = "Cette méthode permet de rechercher" +
            "une vente par son code", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vente trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune vente existe dans la BDD avec le code fourni")
    })
    VentesDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping(VENTES_ENDPOINT + "/all")
    @ApiOperation(value = "Renvoi la liste des ventes", notes = "Cette méthode permet lister" +
            "les ventes", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lister des ventes / Une liste vide")
    })
    List<VentesDto> findAll();

    @DeleteMapping(VENTES_ENDPOINT + "/delete/{idVente}")
    @ApiOperation(value = "Supprimer une vente", notes = "Cette méthode permet de supprimer" +
            "un vente par son ID", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vente supprimée"),
    })
    void delete(@PathVariable("idVente") Integer id);
}
