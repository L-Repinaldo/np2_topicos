package com.biblioteca.np2.domain.dto.User;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
}


