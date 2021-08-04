package br.com.askufcg.services.search;

import br.com.askufcg.exceptions.BadRequestException;
import br.com.askufcg.models.Question;
import br.com.askufcg.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {
    public static final String VOTE = "vote";
    public static final String RELEVANT = "relevant";
    public static final String ANSWERED = "answered";
    public static final String NEW = "new";

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> search(String title, Set<String> tags, String filter) {
        var questions = questionRepository.findBySimilarity(title);
        if(tags != null) {
            questions = questions.stream()
                                .filter(question -> question.getTags().stream().anyMatch(tags::contains))
                                .collect(Collectors.toList());
        }

        if(!NEW.equals(filter)) {
            questions = updateQuestionsByFilter(questions, filter);
        }
        return questions;
    }

    private List<Question> updateQuestionsByFilter(List<Question> questions, String filter) {
        var validFilters = Set.of(VOTE, RELEVANT, ANSWERED);
        if(!validFilters.contains(filter)) {
            throw new BadRequestException("Invalid filter.");
        }

        List<Question> newQuestions = null;
        if(VOTE.equals(filter)) newQuestions = updateQuestionsByVote(questions);
        if(RELEVANT.equals(filter)) newQuestions = updateQuestionsByRelevance(questions);
        if(ANSWERED.equals(filter)) newQuestions = updateQuestionsByAnswers(questions);

        return newQuestions;
    }

    private List<Question> updateQuestionsByAnswers(List<Question> questions) {
        return questions.stream().filter(Question::isAnswered).collect(Collectors.toList());
    }

    private List<Question> updateQuestionsByRelevance(List<Question> questions) {
        Comparator<Question> byRelevance = (Question q1, Question q2) -> {
            return (q2.getQtdLikes() - q2.getQtdDislikes()) - (q1.getQtdLikes() - q1.getQtdDislikes());
        };

        var newQuestions = new ArrayList<>(questions);
        newQuestions.sort(byRelevance);

        return newQuestions;
    }

    private List<Question> updateQuestionsByVote(List<Question> questions) {
        Comparator<Question> byLike = (Question q1, Question q2) -> q2.getQtdLikes() - q1.getQtdLikes();
        questions.sort(byLike);

        var newQuestions = new ArrayList<>(questions);
        newQuestions.sort(byLike);

        return newQuestions;
    }
}
