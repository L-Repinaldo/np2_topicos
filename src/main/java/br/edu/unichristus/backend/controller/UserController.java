package br.edu.unichristus.backend.controller;

import br.edu.unichristus.backend.domain.dto.UserDTO;
import br.edu.unichristus.backend.domain.dto.UserLowDTO;
import br.edu.unichristus.backend.domain.model.User;
import br.edu.unichristus.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public UserDTO create(@RequestBody UserDTO user){
        return service.create(user);
    }

    @GetMapping("/all")
    public List<UserLowDTO> getAll(){
        return service.getAll();
    }

    @PutMapping
    public UserDTO update(@RequestBody UserDTO user){
        return service.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id){
        service.deleteUserById(id);
    }

    @GetMapping("/{id}")
    public UserLowDTO findById(@PathVariable(name = "id") Long id){
       return service.findUserById(id);
    }

}
