package br.com.askufcg.services.answer;

import br.com.askufcg.models.Answer;
import br.com.askufcg.models.User;

import java.util.List;

public interface AnswerService {
    public Answer saveAnswer(Answer answer, User user, Long questionId);
    public Answer updateAnswer(Answer answer, Long answerId);
    public Answer getAnswerById(Long answerId);
    public void deleteAnswerById(Long answerId);
    public List<Answer> getUserAnswers(User user);
}
