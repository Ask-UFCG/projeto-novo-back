package br.com.askufcg.serviceInterface;

import br.com.askufcg.dto.PostCommentDTO;
import br.com.askufcg.models.Comment;

public interface CommentServiceImpl {
    public Comment addComment(PostCommentDTO comment, Long userId);
}
