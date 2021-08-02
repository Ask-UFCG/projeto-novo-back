package br.com.askufcg.dtos.question;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class QuestionRequest {
    private String title;
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Set<String> tags;
}
