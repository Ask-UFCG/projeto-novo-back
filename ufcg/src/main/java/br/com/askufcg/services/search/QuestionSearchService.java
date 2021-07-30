package br.com.askufcg.services.search;

import br.com.askufcg.models.Question;

import java.util.List;

public interface QuestionSearchService {
    public List<Question> search(String title);
}
