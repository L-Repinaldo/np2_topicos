package br.edu.unichristus.backend.controller;

import br.edu.unichristus.backend.domain.dto.ErrorDTO;
import br.edu.unichristus.backend.domain.dto.UserDTO;
import br.edu.unichristus.backend.domain.dto.UserLowDTO;
import br.edu.unichristus.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "API para gerenciamento de usuarios")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "atualiza os dados de um usuario existente.", tags = "User")
    @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Dados invalidos fornecidos")
    @PutMapping
    public UserDTO update(@RequestBody UserDTO user){
        return service.update(user);
    }

    @Operation(summary = "cadastra dados referente a usuarios.", tags = "User")
    @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso!")
    @ApiResponse(responseCode = "400", description = "-possiveis causas" + "O login informado ja existe" )
    @PostMapping
    public UserDTO create(@RequestBody UserDTO user){
        return service.create(user);
    }

    @Operation(summary = "retornar os dados de um usuario com base no ID.", tags = "User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorno dos dados do usuario"),
            @ApiResponse(responseCode = "404", description = "O usuario com o ID informado nao foi encontrado", content = @Content(mediaType = "aplication/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/{id}")
    public UserLowDTO findById(@PathVariable(name = "id") Long id){
       return service.findUserById(id);
    }

    @Operation(summary = "remove um usuario do sistema com base no ID.", tags = "User")
    @ApiResponse(responseCode = "200", description = "Usuario removido com sucesso!")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id){
        service.deleteUserById(id);
    }

    @Operation(summary = "retorna todos os usuarios cadastrados no sistema.", tags = "User")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada com sucesso!")
    @GetMapping("/all")
    public List<UserLowDTO> getAll(){
        return service.getAll();
    }

}
