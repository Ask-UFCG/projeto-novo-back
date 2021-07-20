package br.com.askufcg.services.comment;

import br.com.askufcg.dtos.PostCommentDTO;
import br.com.askufcg.models.Comment;

import java.util.List;

public interface CommentServiceImpl {
    public Comment addCommentAnswer(PostCommentDTO comment, Long userId, Long answerId);
    public Comment addCommentQuestion(PostCommentDTO comment, Long userId, Long questionId);
    public Comment exhibitCommentAnswer(Long commentId, Long answerid);
    public List<Comment> exhibitAllCommentsAnswer(Long answerId);
}
