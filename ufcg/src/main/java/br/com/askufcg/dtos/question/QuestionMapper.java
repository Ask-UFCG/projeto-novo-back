package br.com.askufcg.dtos.question;

import br.com.askufcg.dtos.answer.AnswerMapper;
import br.com.askufcg.dtos.answer.AnswerResponse;
import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AnswerMapper answerMapper;

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
        var author = userMapper.fromUserToResponse(question.getAuthor());
        List<AnswerResponse> answers = null;
        if (question.getAnswers() != null) {
            answers = question.getAnswers().stream()
                    .map(a -> answerMapper.fromAnswer(a))
                    .collect(Collectors.toList());
        }
        return QuestionResponse.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .id(question.getId())
                .qtdDislikes(question.getQtdDislikes())
                .qtdLikes(question.getQtdLikes())
                .answered(question.isAnswered())
                .author(author)
                .tags(question.getTags())
                .answers(answers)
                .build();
    }
}
