package br.com.askufcg.repositories;

import br.com.askufcg.models.Answer;
import br.com.askufcg.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByAuthor(User author);
}
