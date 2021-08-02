package br.com.askufcg.dtos.question;

import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.dtos.user.UserResponse;
import br.com.askufcg.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class QuestionMapper {

    @Autowired
    private UserMapper userMapper;

    public Question toQuestionPOST(QuestionRequest questionRequest) {
        return Question.builder()
                .comments(new ArrayList<>())
                .title(questionRequest.getTitle())
                .content(questionRequest.getContent())
                .createdAt(new Date())
                .qtdLikes(questionRequest.getQtdLikes())
                .qtdDislikes(questionRequest.getQtdDislikes())
                .tags(questionRequest.getTags())
                .answered(false)
                .build();
    }
    public Question toQuestionPUT(QuestionRequest questionRequest) {
        return Question.builder()
                .title(questionRequest.getTitle())
                .content(questionRequest.getContent())
                .qtdLikes(questionRequest.getQtdLikes())
                .qtdDislikes(questionRequest.getQtdDislikes())
                .tags(questionRequest.getTags())
                .build();
    }

    public QuestionResponse fromQuestion(Question question) {
        UserResponse author = userMapper.fromUserToResponse(question.getAuthor());

        return QuestionResponse.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .id(question.getId())
                .qtdDislikes(question.getQtdDislikes())
                .qtdLikes(question.getQtdLikes())
                .answered(question.getAnswered())
                .author(author)
                .tags(question.getTags())
                .build();
    }
}
