package br.com.askufcg.dtos.question;

import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    @Autowired
    private UserMapper userMapper;

    public QuestionResponse fromQuestion(Question question) {
        var author = userMapper.fromUserToResponse(question.getAuthor());

        return QuestionResponse.builder()
                .title(question.getTitle())
                .answered(question.getAnswered())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .id(question.getId())
                .tags(question.getTags())
                .qtdDislikes(question.getQtdDislikes())
                .qtdLikes(question.getQtdLikes())
                .author(author)
                .build();
    }
}
