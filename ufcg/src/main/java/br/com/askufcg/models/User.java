package br.com.askufcg.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String linkAvatar;
    private String linkLinkedin;
    private String linkGithub;
}
