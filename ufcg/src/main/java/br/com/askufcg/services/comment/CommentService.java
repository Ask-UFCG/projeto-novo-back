package br.com.askufcg.services.comment;

import br.com.askufcg.dtos.comment.CommentRequest;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.User;

import java.util.List;

public interface CommentService {
     Comment addCommentAnswer(Comment comment, Long userId, Long answerId);
     Comment addCommentQuestion(Comment comment, Long userId, Long questionId);
     Comment getCommentAnswer(Long commentId, Long answerid);
     List<Comment> getAllCommentsAnswer(Long answerId);
     Comment getCommentQuestion(Long commentId, Long questionId);
     List<Comment> getAllCommentsQuestion(Long questionId);
     Comment deleteCommentAnswer(Long commentId, Long answerId);
     Comment deleteCommentQuestion(Long commentId, Long questionId);
     Comment updateCommentQuestion(CommentRequest comment, Long commentId, Long questionId);
     Comment updateCommentAnswer(CommentRequest commentDTO, Long commentId, Long answerId);
}
