package br.com.askufcg.serviceImpl.comment;

import br.com.askufcg.dto.PostCommentDTO;
import br.com.askufcg.models.Comment;

public interface CommentServiceImpl {
    public Comment addCommentAnswer(PostCommentDTO comment, Long userId, Long answerId);
    public Comment addCommentQuestion(PostCommentDTO comment, Long userId, Long questionId);
}
