package br.com.askufcg.serviceImpl;

import br.com.askufcg.dto.PostCommentDTO;
import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.Answer;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repository.AnswerRepository;
import br.com.askufcg.repository.CommentRepository;
import br.com.askufcg.repository.QuestionRepository;
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
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public Comment addCommentAnswer(PostCommentDTO postCommentDTO, Long userId, Long answerId) {
        Optional<User> user = userRepository.findById(userId);
        isPresent(user, "User not found.");

        Optional<Answer> answer = answerRepository.findById(answerId);
        isPresent(answer, "Answer not found.");

        Comment commentReturn = saveComment(user, postCommentDTO);
        saveAnswer(answer, commentReturn);

        return commentReturn;
    }

    public Comment addCommentQuestion(PostCommentDTO postCommentDTO, Long userId, Long questionId) {
        Optional<User> user = userRepository.findById(userId);
        isPresent(user, "User not found.");


        Optional<Question> question = questionRepository.findById(questionId);
        isPresent(question, "Question not found.");


        Comment commentReturn = saveComment(user, postCommentDTO);
        saveQuestion(question, commentReturn);

        return commentReturn;
    }

    private Comment saveComment(Optional<User> user, PostCommentDTO postCommentDTO){
        Comment comment = new Comment();
        comment.setAuthor(user.get());
        comment.setCreatedAt(new Date());
        comment.setContent(postCommentDTO.getContent());

        return commentRepository.save(comment);
    }

    private void saveAnswer(Optional<Answer> answer, Comment comment){
        Answer answerToBeSaved = answer.get();
        answerToBeSaved.getComments().add(comment);
        answerRepository.save(answerToBeSaved);
    }

    private void saveQuestion(Optional<Question> question, Comment comment){
        Question questionToBeSaved = question.get();
        questionToBeSaved.getComments().add(comment);
        questionRepository.save(questionToBeSaved);
    }

    private void isPresent(Optional entity, String messsage){
        if (!entity.isPresent()){
            throw new NotFoundException(messsage);
        }
    }
}
