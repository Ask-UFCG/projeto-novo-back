package br.com.askufcg.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor @Getter @Setter @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Lob
    private String content;
    private Date createdAt;

    @ManyToOne
    private User author;
}
