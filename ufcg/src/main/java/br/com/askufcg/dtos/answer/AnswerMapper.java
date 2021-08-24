package br.com.askufcg.dtos.answer;

import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.models.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class AnswerMapper {
    @Autowired
    private UserMapper userMapper;

    public Answer toAnswerPOST(AnswerRequest answerRequest) {
        return Answer.builder()
                .comments(new ArrayList<>())
                .content(answerRequest.getContent())
                .createdAt(new Date())
                .qtdLikes(answerRequest.getQtdLikes())
                .qtdDislikes(answerRequest.getQtdDislikes())
                .solution(answerRequest.getSolution())
                .build();
    }

    public Answer toAnswerPUT(AnswerRequest answerRequest) {
        return Answer.builder()
                .content(answerRequest.getContent())
                .qtdLikes(answerRequest.getQtdLikes())
                .qtdDislikes(answerRequest.getQtdDislikes())
                .solution(answerRequest.getSolution())
                .build();
    }

    public AnswerResponse fromAnswer(Answer answer) {
        var author = userMapper.fromUserToResponse(answer.getAuthor());

        return AnswerResponse.builder()
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .id(answer.getId())
                .comments(answer.getComments())
                .qtdDislikes(answer.getQtdDislikes())
                .qtdLikes(answer.getQtdLikes())
                .solution(answer.getSolution())
                .author(author)
                .build();
    }
}
