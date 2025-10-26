package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.domain.dto.User.UserDto;
import com.biblioteca.np2.domain.dto.User.UserLowDto;
import com.biblioteca.np2.service.UserService;
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
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou login já existe")
    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        return service.create(user);
    }

    @Operation(summary = "Lista todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping("/all")
    public List<UserLowDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Busca usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public UserLowDto findById(@PathVariable(name = "id") Long id) {
        return service.findUserById(id);
    }

    @Operation(summary = "Atualiza dados de um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping
    public UserDto update(@RequestBody UserDto user) {
        return service.update(user);
    }

    @Operation(summary = "Remove um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteUserById(id);
    }
}


