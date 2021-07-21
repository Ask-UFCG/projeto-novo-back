package br.com.askufcg.services.comment;

import br.com.askufcg.dtos.user.PostCommentDTO;
import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.Answer;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.AnswerRepository;
import br.com.askufcg.repositories.CommentRepository;
import br.com.askufcg.repositories.QuestionRepository;
import br.com.askufcg.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
@AllArgsConstructor
public class CommentService implements CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Comment addCommentAnswer(PostCommentDTO postCommentDTO, Long userId, Long answerId) {
        Optional<User> user = userRepository.findById(userId);
        checkEntityNotFound(user, "User not found.");

        Optional<Answer> answer = answerRepository.findById(answerId);
        checkEntityNotFound(answer, "Answer not found.");

        Comment commentReturn = saveComment(user.get(), postCommentDTO.getContent());
        saveCommentInAnswer(answer.get(), commentReturn);

        return commentReturn;
    }

    public Comment addCommentQuestion(PostCommentDTO postCommentDTO, Long userId, Long questionId) {
        Optional<User> user = userRepository.findById(userId);
        checkEntityNotFound(user, "User not found.");

        Optional<Question> question = questionRepository.findById(questionId);
        checkEntityNotFound(question, "Question not found.");

        Comment commentReturn = saveComment(user.get(), postCommentDTO.getContent());
        saveCommentInQuestion(question.get(), commentReturn);

        return commentReturn;
    }

    public Comment exhibitCommentAnswer(Long commentId, Long answerId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, "Comment not found.");

        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        checkEntityNotFound(answerOptional, "Answer not found.");

        Comment comment = commentOptional.get();
        Answer answer = answerOptional.get();
        if (!answer.getComments().contains(comment)){
            throw new NotFoundException("Answer not found within answer.");
        }

        return comment;
    }

    public List<Comment> exhibitAllCommentsAnswer(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        checkEntityNotFound(answer, "Answer not found.");

        return answer.get().getComments();
    }

    public Comment exhibitCommentQuestion(Long commentId, Long questionId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, "Comment not found.");

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        checkEntityNotFound(questionOptional, "Question not found.");

        Comment comment = commentOptional.get();
        Question question = questionOptional.get();
        if (!question.getComments().contains(comment)){
            throw new NotFoundException("Comment not found within answer.");
        }

        return comment;
    }

    public List<Comment> exhibitAllCommentsQuestion(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        checkEntityNotFound(question, "Answer not found.");

        return question.get().getComments();
    }

    public Comment deleteCommentAnswer(Long commentId, Long answerId) {
        checkCommentAndAnswer(commentId, answerId);

        Comment comment = commentRepository.findById(commentId).get();
        Answer answer = answerRepository.findById(answerId).get();

        answer.getComments().remove(comment);
        answerRepository.save(answer);
        commentRepository.delete(comment);
        return comment;

    }

    public Comment deleteCommentQuestion(Long commentId, Long questionId) {
        checkCommentAndQuestion(commentId, questionId);

        Comment comment = commentRepository.findById(commentId).get();
        Question question = questionRepository.findById(questionId).get();

        question.getComments().remove(comment);
        questionRepository.save(question);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment updateCommentQuestion(PostCommentDTO commentDTO, Long commentId, Long questionId) {
        checkCommentAndQuestion(commentId, questionId);

        Comment comment = commentRepository.findById(commentId).get();
        Question question = questionRepository.findById(questionId).get();

        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);
        questionRepository.save(question);
        return comment;

    }

    public Comment updateCommentAnswer(PostCommentDTO commentDTO, Long commentId, Long answerId) {
        checkCommentAndAnswer(commentId, answerId);

        Comment comment = commentRepository.findById(commentId).get();
        Answer answer = answerRepository.findById(answerId).get();

        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);
        answerRepository.save(answer);
        return comment;
    }

    private Comment  saveComment(User user, String content){
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setCreatedAt(new Date());
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    private void saveCommentInAnswer(Answer answer, Comment comment){
        answer.getComments().add(comment);
        answerRepository.save(answer);
    }

    private void saveCommentInQuestion(Question question, Comment comment){
        question.getComments().add(comment);
        questionRepository.save(question);
    }

    private void checkCommentAndAnswer(Long commentId, Long answerId){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, "Comment not found");

        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        checkEntityNotFound(answerOptional, "Answer not found");

        Comment comment = commentOptional.get();
        Answer answer = answerOptional.get();
        if (!answer.getComments().contains(comment)){
            throw new NotFoundException("Comment not found within answer.");
        }
    }

    private void checkCommentAndQuestion(Long commentId, Long questionId){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, "Comment not found");

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        checkEntityNotFound(questionOptional, "Question not found");

        Comment comment = commentOptional.get();
        Question question = questionOptional.get();
        if (!question.getComments().contains(comment)){
            throw new NotFoundException("Comment not found within answer.");
        }
    }
}
