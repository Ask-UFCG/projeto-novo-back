package br.com.askufcg.services.comment;

import br.com.askufcg.exceptions.Constants;
import br.com.askufcg.exceptions.NotFoundException;
import br.com.askufcg.models.Answer;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.AnswerRepository;
import br.com.askufcg.repositories.CommentRepository;
import br.com.askufcg.repositories.QuestionRepository;
import br.com.askufcg.repositories.UserRepository;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public Comment addCommentAnswer(Comment comment, Long userId, Long answerId) {
        Optional<User> user = userRepository.findById(userId);
        checkEntityNotFound(user, Constants.USER_NOT_FOUND);

        Optional<Answer> answer = answerRepository.findById(answerId);
        checkEntityNotFound(answer, Constants.ANSWER_NOT_FOUND);

        User author = user.get();
        comment.setAuthor(author);
        commentRepository.save(comment);
        saveCommentInAnswer(answer.get(), comment);

        return comment;
    }

    public Comment addCommentQuestion(Comment comment, Long userId, Long questionId) {
        Optional<User> user = userRepository.findById(userId);
        checkEntityNotFound(user, Constants.USER_NOT_FOUND);

        Optional<Question> question = questionRepository.findById(questionId);
        checkEntityNotFound(question, Constants.QUESTION_NOT_FOUND);

        User author = user.get();
        comment.setAuthor(author);
        commentRepository.save(comment);
        saveCommentInQuestion(question.get(), comment);

        return comment;
    }

    public Comment getCommentAnswer(Long commentId, Long answerId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, Constants.COMMENT_NOT_FOUND);

        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        checkEntityNotFound(answerOptional, Constants.ANSWER_NOT_FOUND);

        Comment comment = commentOptional.get();
        Answer answer = answerOptional.get();
        if (!answer.getComments().contains(comment)){
            throw new NotFoundException(Constants.ANSWER_NOT_FOUND);
        }

        return comment;
    }

    public List<Comment> getAllCommentsAnswer(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        checkEntityNotFound(answer, Constants.ANSWER_NOT_FOUND);

        return answer.get().getComments();
    }

    public Comment getCommentQuestion(Long commentId, Long questionId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, Constants.COMMENT_NOT_FOUND);

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        checkEntityNotFound(questionOptional, Constants.QUESTION_NOT_FOUND);

        Comment comment = commentOptional.get();
        Question question = questionOptional.get();
        if (!question.getComments().contains(comment)){
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        }

        return comment;
    }

    public List<Comment> getAllCommentsQuestion(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        checkEntityNotFound(question, Constants.ANSWER_NOT_FOUND);

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

    public Comment updateCommentQuestion(Comment comment, Long commentId, Long questionId) {
        checkCommentAndQuestion(commentId, questionId);

        Comment originalComment = commentRepository.findById(commentId).get();
        Question question = questionRepository.findById(questionId).get();

        originalComment.setContent(comment.getContent());
        commentRepository.save(originalComment);
        questionRepository.save(question);
        return originalComment;

    }

    public Comment updateCommentAnswer(Comment comment, Long commentId, Long answerId) {
        checkCommentAndAnswer(commentId, answerId);

        Comment originalComment = commentRepository.findById(commentId).get();
        Answer answer = answerRepository.findById(answerId).get();

        originalComment.setContent(comment.getContent());
        commentRepository.save(originalComment);
        answerRepository.save(answer);
        return originalComment;
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
        checkEntityNotFound(commentOptional, Constants.COMMENT_NOT_FOUND);

        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        checkEntityNotFound(answerOptional, Constants.ANSWER_NOT_FOUND);

        Comment comment = commentOptional.get();
        Answer answer = answerOptional.get();
        if (!answer.getComments().contains(comment)){
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        }
    }

    private void checkCommentAndQuestion(Long commentId, Long questionId){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        checkEntityNotFound(commentOptional, Constants.COMMENT_NOT_FOUND);

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        checkEntityNotFound(questionOptional, Constants.QUESTION_NOT_FOUND);

        Comment comment = commentOptional.get();
        Question question = questionOptional.get();
        if (!question.getComments().contains(comment)){
            throw new NotFoundException(Constants.COMMENT_NOT_FOUND);
        }
    }
}
