package br.com.askufcg.models;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor @Getter @Setter @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @Table(name = "tb_question")
public class Question {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Date createdAt;
    private Boolean answered;

    @ElementCollection
    private Set<String> tags;

    @ManyToOne
    private User author;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @OneToMany
    private List<Comment> comments;
}
