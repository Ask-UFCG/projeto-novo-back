package br.com.askufcg.dtos.question;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
public class QuestionRequest {
    @NotEmpty(message = "O título não pode ser vazio.")
    private String title;
    @NotEmpty(message = "A questão não pode ser vazia.")
    private String content;
    private Set<String> tags;
    private Boolean answered;
}
