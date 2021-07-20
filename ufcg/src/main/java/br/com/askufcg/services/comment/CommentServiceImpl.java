package br.com.askufcg.services.comment;

import br.com.askufcg.dtos.PostCommentDTO;
import br.com.askufcg.models.Comment;

import java.util.List;

public interface CommentServiceImpl {
     Comment addCommentAnswer(PostCommentDTO comment, Long userId, Long answerId);
     Comment addCommentQuestion(PostCommentDTO comment, Long userId, Long questionId);
     Comment exhibitCommentAnswer(Long commentId, Long answerid);
     List<Comment> exhibitAllCommentsAnswer(Long answerId);
     Comment exhibitCommentQuestion(Long commentId, Long questionId);
     List<Comment> exhibitAllCommentsQuestion(Long questionId);
}
