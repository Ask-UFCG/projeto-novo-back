package br.com.askufcg.dtos.comment;

import br.com.askufcg.models.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CommentRequest {
    @NotEmpty(message = "O comentário não pode ser vazio.")
    private String content;
    private User author;
    private Date createdAt;
}
