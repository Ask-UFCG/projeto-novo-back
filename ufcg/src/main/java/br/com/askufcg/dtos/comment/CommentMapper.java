package br.com.askufcg.dtos.comment;

import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.dtos.user.UserResponse;
import br.com.askufcg.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {
    @Autowired
    private UserMapper userMapper;

    public Comment toCommentPOST(CommentRequest commentRequest){
        return Comment.builder()
                .content(commentRequest.getContent())
                .createdAt(new Date())
                .build();
    }

    public CommentResponse fromComment(Comment commentResult) {
        UserResponse author = userMapper.fromUserToResponse(commentResult.getAuthor());
        return CommentResponse.builder()
                .id(commentResult.getId())
                .content(commentResult.getContent())
                .createdAt(commentResult.getCreatedAt())
                .author(author)
                .build();
    }
    public Comment toCommentPUT(CommentRequest commentRequest) {
        return Comment.builder()
               .content(commentRequest.getContent())
                .build();
    }
}
