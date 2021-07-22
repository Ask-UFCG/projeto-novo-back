package br.com.askufcg.services.comment;

import br.com.askufcg.dtos.comment.PostCommentDTO;
import br.com.askufcg.models.Comment;

import java.util.List;

public interface CommentService {
     Comment addCommentAnswer(PostCommentDTO comment, Long userId, Long answerId);
     Comment addCommentQuestion(PostCommentDTO comment, Long userId, Long questionId);
     Comment getCommentAnswer(Long commentId, Long answerid);
     List<Comment> getAllCommentsAnswer(Long answerId);
     Comment getCommentQuestion(Long commentId, Long questionId);
     List<Comment> getAllCommentsQuestion(Long questionId);
     Comment deleteCommentAnswer(Long commentId, Long answerId);
     Comment deleteCommentQuestion(Long commentId, Long questionId);
     Comment updateCommentQuestion(PostCommentDTO comment, Long commentId, Long questionId);
}
