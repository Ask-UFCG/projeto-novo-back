package br.com.askufcg.services.search;

import br.com.askufcg.models.Question;
import br.com.askufcg.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override

    public List<Question> search(String title) {
        return questionRepository.findBySimilarity(title);
    }
}
