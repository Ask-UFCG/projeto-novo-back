package br.com.askufcg.dtos.user;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class UserRequest {
    @Email(message = "Email inválido.")
    private String email;
    @NotEmpty(message = "A senha não pode ser vazia.")
    private String password;
    @NotEmpty(message = "O primeiro nome não pode ser vazio.")
    private String firstName;
    @NotEmpty(message = "O sobrenome não pode ser vazio.")
    private String lastName;
    private String linkAvatar;
    private String linkLinkedin;
    private String linkGithub;
}
