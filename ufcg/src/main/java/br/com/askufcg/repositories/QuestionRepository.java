package br.com.askufcg.repositories;

import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findQuestionsByAuthor(User author);

}
