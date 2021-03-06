package br.com.askufcg.controllers;

import br.com.askufcg.dtos.question.QuestionMapper;
import br.com.askufcg.dtos.question.QuestionRequest;
import br.com.askufcg.dtos.question.QuestionRequestPUT;
import br.com.askufcg.dtos.question.QuestionResponse;
import br.com.askufcg.models.Question;
import br.com.askufcg.models.User;
import br.com.askufcg.services.question.QuestionService;
import br.com.askufcg.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/users/{id}")
    public ResponseEntity<List<QuestionResponse>> getUserQuestions(@PathVariable Long id) {
        User user = userService.getUserById(id);
        List<Question> questions = questionService.getUserQuestions(user);
        List<QuestionResponse> questionsResponse = questions.stream()
                .map(a -> questionMapper.fromQuestion(a))
                .collect(Collectors.toList());
        return new ResponseEntity<>(questionsResponse, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<QuestionResponse> saveQuestion(@RequestBody @Valid QuestionRequest questionRequest, @PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Question question = questionMapper.toQuestionPOST(questionRequest);
        Question savedQuestion = questionService.saveQuestion(question, user);
        QuestionResponse questionResponse = questionMapper.fromQuestion(savedQuestion);
        return new ResponseEntity<>(questionResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}/users/{userId}/like")
    public ResponseEntity<QuestionResponse> likeQuestion(@PathVariable Long questionId, @PathVariable Long userId) {
        User __ = userService.getUserById(userId);
        Question question = questionService.getQuestionById(questionId);
        Question questionLiked = questionService.likeQuestion(question, userId);
        QuestionResponse questionResponse = questionMapper.fromQuestion(questionLiked);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @GetMapping("/{questionId}/users/{userId}/dislike")
    public ResponseEntity<QuestionResponse> dislikeQuestion(@PathVariable Long questionId, @PathVariable Long userId) {
        User __ = userService.getUserById(userId);
        Question question = questionService.getQuestionById(questionId);
        Question questionDisliked = questionService.dislikeQuestion(question, userId);
        QuestionResponse questionResponse = questionMapper.fromQuestion(questionDisliked);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}/users/{userId}/like")
    public ResponseEntity<QuestionResponse> removeLike(@PathVariable Long questionId, @PathVariable Long userId) {
        User __ = userService.getUserById(userId);
        Question question = questionService.getQuestionById(questionId);
        Question questionUpdated = questionService.removeLikeQuestion(question, userId);
        QuestionResponse questionResponse = questionMapper.fromQuestion(questionUpdated);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}/users/{userId}/dislike")
    public ResponseEntity<QuestionResponse> removeDislike(@PathVariable Long questionId, @PathVariable Long userId) {
        User __ = userService.getUserById(userId);
        Question question = questionService.getQuestionById(questionId);
        Question questionUpdated = questionService.removeDislikeQuestion(question, userId);
        QuestionResponse questionResponse = questionMapper.fromQuestion(questionUpdated);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody @Valid QuestionRequestPUT questionRequest, @PathVariable Long questionId) {
        Question question = questionMapper.toQuestionPUT(questionRequest);
        Question updatedQuestion = questionService.updateQuestion(questionId, question);
        QuestionResponse questionResponse = questionMapper.fromQuestion(updatedQuestion);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestionById(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);
        QuestionResponse questionResponse = questionMapper.fromQuestion(question);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

}
