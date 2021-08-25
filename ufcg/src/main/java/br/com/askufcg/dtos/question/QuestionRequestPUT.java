package br.com.askufcg.dtos.question;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class QuestionRequestPUT {
    private String title;
    private String content;
    private Set<String> tags;
    private Boolean answered;
}
