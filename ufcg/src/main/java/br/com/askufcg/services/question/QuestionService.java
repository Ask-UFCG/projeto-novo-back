package br.com.askufcg.services.question;

import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;

import java.util.List;

public interface QuestionService {
    public Question getQuestionById(Long id);
    public Question saveQuestion(Question question, User user);
    public Question updateQuestion(Long id, Question newQuestion);
    public void deleteQuestionById(Long id);
    public List<Question> getUserQuestions(User user);

    public Question likeQuestion(Question question, Long userId);
    public Question dislikeQuestion(Question question, Long userId);
    public Question removeDislikeQuestion(Question question, Long userId);
    public Question removeLikeQuestion(Question question, Long userId);
}
