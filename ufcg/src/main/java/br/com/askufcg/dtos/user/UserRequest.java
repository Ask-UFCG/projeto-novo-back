package br.com.askufcg.dtos.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String linkAvatar;
    private String linkLinkedin;
    private String linkGithub;
}
