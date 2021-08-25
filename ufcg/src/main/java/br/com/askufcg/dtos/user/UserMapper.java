package br.com.askufcg.dtos.user;

import br.com.askufcg.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse fromUserToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .linkAvatar(user.getLinkAvatar())
                .linkGithub(user.getLinkGithub())
                .linkLinkedin(user.getLinkLinkedin())
                .build();
    }

    public User toUserPUT(UserRequestPUT userRequestPUT) {
        return User.builder()
                .firstName(userRequestPUT.getFirstName())
                .lastName(userRequestPUT.getLastName())
                .linkAvatar(userRequestPUT.getLinkAvatar())
                .linkGithub(userRequestPUT.getLinkGithub())
                .linkLinkedin(userRequestPUT.getLinkLinkedin())
                .build();
    }

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .linkAvatar(userRequest.getLinkAvatar())
                .linkGithub(userRequest.getLinkGithub())
                .linkLinkedin(userRequest.getLinkLinkedin())
                .build();
    }
}
