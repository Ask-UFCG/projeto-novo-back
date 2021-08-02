package br.com.askufcg.dtos.answer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerRequest {
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Boolean solution;
}
