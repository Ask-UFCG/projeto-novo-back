package br.com.askufcg.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor @Getter @Setter @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @Table(name = "tb_answer")
public class Answer {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Date createdAt;
    private Boolean solution;

    @ManyToOne
    private User author;
    @OneToMany
    private List<Comment> comments;
}
