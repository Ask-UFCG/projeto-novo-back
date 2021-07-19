package br.com.askufcg.serviceImpl;

import br.com.askufcg.models.Comment;
import br.com.askufcg.repository.CommentRepository;
import br.com.askufcg.serviceInterface.CommentServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements CommentServiceImpl {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        Comment commentReturn = null;
        if (!commentRepository.existsById(comment.getId())){
            commentReturn = commentRepository.save(comment);
        }
        return commentReturn;
    }
}
