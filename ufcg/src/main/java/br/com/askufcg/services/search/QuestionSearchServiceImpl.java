package br.com.askufcg.services.search;

import br.com.askufcg.models.Question;
import br.com.askufcg.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> search(String title, Set<String> tags) {
        var questions = questionRepository.findBySimilarity(title);
        if(tags != null) {
            questions = questions.stream()
                                .filter(question -> question.getTags().stream().anyMatch(tags::contains))
                                .collect(Collectors.toList());
        }
        return questions;
    }
}
