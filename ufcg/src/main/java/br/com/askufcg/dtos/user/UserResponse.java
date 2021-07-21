package br.com.askufcg.dtos.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String linkAvatar;
    private String linkLinkedin;
    private String linkGithub;
}
