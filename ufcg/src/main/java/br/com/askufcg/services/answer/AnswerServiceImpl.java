package br.com.askufcg.services.answer;

import br.com.askufcg.models.Answer;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.AnswerRepository;
import br.com.askufcg.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Answer saveAnswer(Answer answer, User user, Long questionId) {
        var optionalQuestion = questionRepository.findById(questionId);
        checkEntityNotFound(optionalQuestion, "Question not found.");

        var question = optionalQuestion.get();

        answer.setAuthor(user);
        answer.setQuestion(question);

        answerRepository.save(answer);
        saveAnswerInQuestion(answer, question);

        return answer;
    }

    private void saveAnswerInQuestion(Answer answer, Question question) {
        question.getAnswers().add(answer);
        questionRepository.save(question);
    }

    @Override
    public Answer updateAnswer(Answer answer, Long answerId) {
        var answerToUpdate = getAnswerById(answerId);

        var updatedAnswer = updateAllInformationsAnswer(answer, answerToUpdate);

        answerRepository.save(updatedAnswer);
        return updatedAnswer;
    }

    private Answer updateAllInformationsAnswer(Answer answer, Answer answerToUpdate) {
        answerToUpdate.setContent(answer.getContent());
        answerToUpdate.setComments(answer.getComments());
        answerToUpdate.setQtdLikes(answer.getQtdLikes());
        answerToUpdate.setQtdDislikes(answer.getQtdDislikes());
        answerToUpdate.setSolution(answer.getSolution());

        return answerToUpdate;
    }

    @Override
    public Answer getAnswerById(Long answerId) {
        var optionalAnswer = answerRepository.findById(answerId);
        checkEntityNotFound(optionalAnswer, "Answer not found.");

        return optionalAnswer.get();
    }

    @Override
    public void deleteAnswerById(Long answerId) {
        var answer = getAnswerById(answerId);
        answerRepository.delete(answer);
    }

    @Override
    public List<Answer> getUserAnswers(User user) {
        return answerRepository.findAnswersByAuthor(user);
    }
}
