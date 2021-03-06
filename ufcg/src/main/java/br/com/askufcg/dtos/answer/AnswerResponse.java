package br.com.askufcg.dtos.answer;

import br.com.askufcg.dtos.user.UserResponse;
import br.com.askufcg.models.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class AnswerResponse {
    private Long id;
    private String content;
    private Integer qtdLikes;
    private Integer qtdDislikes;
    private Date createdAt;
    private Boolean solution;
    private UserResponse author;
    private List<Comment> comments;
}
