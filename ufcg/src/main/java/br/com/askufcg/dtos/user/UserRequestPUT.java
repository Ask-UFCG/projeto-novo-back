package br.com.askufcg.dtos.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestPUT {
    private String firstName;
    private String lastName;
    private String linkAvatar;
    private String linkLinkedin;
    private String linkGithub;
}
