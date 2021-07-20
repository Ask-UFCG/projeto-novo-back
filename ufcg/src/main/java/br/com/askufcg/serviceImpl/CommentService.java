package br.com.askufcg.serviceImpl;

import br.com.askufcg.dto.PostCommentDTO;
import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.User;
import br.com.askufcg.repository.CommentRepository;
import br.com.askufcg.repository.UserRepository;
import br.com.askufcg.serviceInterface.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService implements CommentServiceImpl {
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    public Comment addComment(PostCommentDTO comment, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new NotFoundException("User not found.");
        }

        Comment comment1 = new Comment();
        comment1.setAuthor(user.get());
        comment1.setCreatedAt(new Date());
        comment1.setContent(comment.getContent());

        return commentRepository.save(comment1);
    }
}
