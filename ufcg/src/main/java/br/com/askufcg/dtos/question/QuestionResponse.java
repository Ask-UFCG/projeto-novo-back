package br.com.askufcg.dtos.question;

import br.com.askufcg.dtos.answer.AnswerResponse;
import br.com.askufcg.dtos.user.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class QuestionResponse {
    private Long id;
    private String title;
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Boolean answered;
    private Set<Long> usersLike;
    private Set<Long> usersDislike;
    private Date createdAt;
    private UserResponse author;
    private Set<String> tags;
    private List<AnswerResponse> answers;
}
