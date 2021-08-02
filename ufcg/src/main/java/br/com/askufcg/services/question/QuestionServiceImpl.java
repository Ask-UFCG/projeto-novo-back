package br.com.askufcg.services.question;

import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        checkEntityNotFound(optionalQuestion, "Question not found");

        return optionalQuestion.get();
    }

    @Override
    public Question saveQuestion(Question question, User user) {
        question.setAuthor(user);
        questionRepository.save(question);
        return question;
    }

    @Override
    public Question updateQuestion(Long id, Question newQuestion) {
        Question questionToUpdate = getQuestionById(id);

        Question updatedQuestion = updateAllInformationQuestion(newQuestion, questionToUpdate);

        questionRepository.save(updatedQuestion);

        return updatedQuestion;
    }

    private Question updateAllInformationQuestion(Question question, Question questionToUpdate) {
        questionToUpdate.setTitle(question.getTitle());
        questionToUpdate.setContent(question.getContent());
        questionToUpdate.setQtdLikes(question.getQtdLikes());
        questionToUpdate.setQtdDislikes(question.getQtdDislikes());
        questionToUpdate.setTags(question.getTags());

        return questionToUpdate;
    }

    @Override
    public void deleteQuestionById(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }

    @Override
    public List<Question> getUserQuestions(User user) {
        return questionRepository.findQuestionByAuthor(user);
    }
}
