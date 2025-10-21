package com.biblioteca.np2.service;

import com.biblioteca.np2.domain.dto.User.UserDto;
import com.biblioteca.np2.domain.dto.User.UserLowDto;
import com.biblioteca.np2.domain.model.User;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.UserRepository;
import com.biblioteca.np2.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    public UserDto create(UserDto dto) {
        log.info("UserService: Criando usuário - login: {}", dto.getLogin());
        
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "np2.service.user.badrequest",
                    "O nome do usuário é obrigatório.");
        }

        if (dto.getName().length() > 100) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "np2.service.user.badrequest",
                    "O limite de caracteres do nome é 100.");
        }

        if (dto.getLogin() == null || dto.getLogin().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "np2.service.user.badrequest",
                    "O login é obrigatório.");
        }

        // Verificar se login já existe
        if (repository.findByLoginIgnoreCase(dto.getLogin()).isPresent()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "np2.service.user.badrequest",
                    "O login informado já existe.");
        }

        var user = MapperUtil.parseObject(dto, User.class);
        var userPersist = repository.save(user);
        
        log.info("UserService: Usuário criado com ID: {}", userPersist.getId());
        return MapperUtil.parseObject(userPersist, UserDto.class);
    }

    public List<UserLowDto> getAll() {
        log.info("UserService: Listando todos os usuários");
        return MapperUtil.parseListObject(repository.findAll(), UserLowDto.class);
    }

    public UserDto update(UserDto dto) {
        log.info("UserService: Atualizando usuário ID: {}", dto.getId());
        
        var user = MapperUtil.parseObject(dto, User.class);
        var userPersist = repository.save(user);
        
        return MapperUtil.parseObject(userPersist, UserDto.class);
    }

    public void deleteUserById(Long id) {
        log.info("UserService: Deletando usuário ID: {}", id);
        repository.deleteById(id);
    }

    public UserLowDto findUserById(Long id) {
        log.info("UserService: Buscando usuário ID: {}", id);
        
        var user = repository.findById(id).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND,
                        "np2.service.user.notfound",
                        "O usuário não foi encontrado.")
        );
        
        return MapperUtil.parseObject(user, UserLowDto.class);
    }
}


