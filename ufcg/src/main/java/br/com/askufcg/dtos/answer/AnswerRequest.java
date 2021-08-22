package br.com.askufcg.dtos.answer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AnswerRequest {
    @NotEmpty(message = "A resposta não pode ser vazia.")
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Boolean solution;
}
