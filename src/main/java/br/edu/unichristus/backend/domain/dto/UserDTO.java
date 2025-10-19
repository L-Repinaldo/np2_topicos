
package br.edu.unichristus.backend.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;

}










