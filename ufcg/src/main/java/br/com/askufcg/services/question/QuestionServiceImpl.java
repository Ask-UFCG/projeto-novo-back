package br.com.askufcg.services.question;

import br.com.askufcg.exceptions.Constants;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.askufcg.utils.Util.checkEntityNotFound;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        checkEntityNotFound(optionalQuestion, Constants.QUESTION_NOT_FOUND);

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
        if(question.getTitle() != null) {
            questionToUpdate.setTitle(question.getTitle());
        }
        if(question.getContent() != null) {
            questionToUpdate.setContent(question.getContent());
        }
        if(question.getTags() != null) {
            questionToUpdate.setTags(question.getTags());
        }
        questionToUpdate.setAnswered(question.isAnswered());
        return questionToUpdate;
    }

    @Override
    public void deleteQuestionById(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }

    @Override
    public List<Question> getUserQuestions(User user) {
        return questionRepository.findQuestionsByAuthor(user);
    }

    @Override
    public Question likeQuestion(Question question, Long userId) {
        var usersDislike = question.getUsersDislike();
        if(usersDislike.contains(userId)) {
            usersDislike.remove(userId);
        }

        var usersLike = question.getUsersLike();
        usersLike.add(userId);

        updateVotes(question, usersDislike, usersLike);
        return question;
    }

    private void updateVotes(Question question, Set<Long> usersDislike, Set<Long> usersLike) {
        question.setQtdLikes(usersLike.size());
        question.setQtdDislikes(usersDislike.size());
        questionRepository.save(question);
    }

    @Override
    public Question dislikeQuestion(Question question, Long userId) {
        var usersLike = question.getUsersLike();
        if(usersLike.contains(userId)) {
            usersLike.remove(userId);
        }

        var usersDislike = question.getUsersDislike();
        usersDislike.add(userId);

        updateVotes(question, usersDislike, usersLike);
        return question;
    }

    @Override
    public Question removeDislikeQuestion(Question question, Long userId) {
        var usersDislike = question.getUsersDislike();
        usersDislike.remove(userId);

        question.setQtdDislikes(usersDislike.size());

        questionRepository.save(question);
        return question;
    }

    @Override
    public Question removeLikeQuestion(Question question, Long userId) {
        var usersLike = question.getUsersLike();
        usersLike.remove(userId);

        question.setQtdLikes(usersLike.size());

        questionRepository.save(question);
        return question;
    }
}
