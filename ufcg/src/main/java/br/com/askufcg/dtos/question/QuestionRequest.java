package br.com.askufcg.dtos.question;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
public class QuestionRequest {
    @NotEmpty(message = "O título não pode ser vazio.")
    private String title;
    @NotEmpty(message = "A questão não pode ser vazia.")
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Set<String> tags;
}
