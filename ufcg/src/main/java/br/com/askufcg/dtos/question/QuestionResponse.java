package br.com.askufcg.dtos.question;

import br.com.askufcg.dtos.user.UserResponse;
import br.com.askufcg.models.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class QuestionResponse {
    private Long id;
    private String title;
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Date createdAt;
    private Boolean answered;
    private Set<String> tags;
    private UserResponse author;
}
