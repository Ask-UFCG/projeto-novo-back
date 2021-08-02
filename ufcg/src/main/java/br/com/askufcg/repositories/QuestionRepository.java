package br.com.askufcg.repositories;

import br.com.askufcg.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "select * from tb_question order by similarity(title, :title) desc", nativeQuery = true)
    List<Question> findBySimilarity(@Param("title") String title);
}
