package com.adjadev.stock.controllers.api;

import com.adjadev.stock.dto.ChangerMotDePasseUtilisateurDto;
import com.adjadev.stock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adjadev.stock.utils.Constants.UTILISATEUR_ENDPOINT;
import static com.adjadev.stock.utils.Constants.VENTES_ENDPOINT;

@Api("utilisateurs")
public interface UtilisateurAPi {
    @PostMapping(value = UTILISATEUR_ENDPOINT + "/create",
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value ="Enregistrer un utilisateur", notes = "Cette méthode permet d'enregistrer/modifier un utilisateur",
    response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet utilisateur créé / modifier"),
            @ApiResponse(code = 400, message = "L'objet utilisateur n'est pas valide")
    })
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @PostMapping(value = UTILISATEUR_ENDPOINT + "/update/password",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier password d'un utilisateur", notes = "Cette méthode permet de modifier" +
            "le password d'un utilisateur", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le password a bien été modifié"),
            @ApiResponse(code = 400, message = "Le password n'a pas pu être modifier")
    })
    UtilisateurDto changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDto dto);

    @GetMapping(value = UTILISATEUR_ENDPOINT + "/{idUtilisateur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur", notes = "Cette méthode permet de rechercher" +
            "un utilisateur par son ID", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Utilisateur trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur existe dans la BDD avec l'ID fourni")
    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(value = UTILISATEUR_ENDPOINT + "/find/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur", notes = "Cette méthode permet de rechercher" +
            "un utilisateur par son email", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Utilisateur trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur existe dans la BDD avec l'email fourni")
    })
    UtilisateurDto findByEmail(@PathVariable("email") String email);

    @GetMapping(value = UTILISATEUR_ENDPOINT + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des utilisateurs", notes = "Cette méthode permet de lister" +
            "les utilisateurs", responseContainer = "List<UtilisateurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des utilisateurs / Une liste vide"),
    })
    List<UtilisateurDto> findAll();

    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{idUtilisateur}")
    @ApiOperation(value = "Supprimer un utilisateur", notes = "Cette méthode permet de supprimer" +
            "un utilisateur par son ID", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Utilisateur supprimé"),
    })
    void delete(@PathVariable("idUtilisateur") Integer id);
}
