package br.com.askufcg.dtos.comment;

import br.com.askufcg.models.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Date createdAt;
    private User author;
}
