package br.com.askufcg.dtos.comment;

import br.com.askufcg.models.Comment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {
    public Comment toCommentPOST(CommentRequest commentRequest){
        return Comment.builder()
                .content(commentRequest.getContent())
                .createdAt(new Date())
                .build();
    }

    public CommentResponse fromComment(Comment commentResult) {
        return CommentResponse.builder()
                .id(commentResult.getId())
                .content(commentResult.getContent())
                .createdAt(commentResult.getCreatedAt())
                .author(commentResult.getAuthor())
                .build();
    }
    public Comment toCommentPUT(CommentRequest commentRequest) {
        return Comment.builder()
               .content(commentRequest.getContent())
                .build();
    }
}
