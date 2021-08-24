package br.com.askufcg.controllers;

import br.com.askufcg.dtos.answer.AnswerMapper;
import br.com.askufcg.dtos.answer.AnswerRequest;
import br.com.askufcg.dtos.answer.AnswerResponse;
import br.com.askufcg.services.answer.AnswerService;
import br.com.askufcg.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
@CrossOrigin
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerMapper answerMapper;

    @GetMapping("/users/{id}")
    public ResponseEntity<List<AnswerResponse>> getUserAnswers(@PathVariable Long id) {
        var user = userService.getUserById(id);
        var answers = answerService.getUserAnswers(user);
        var answersResponse = answers.stream()
                                                        .map(a -> answerMapper.fromAnswer(a))
                                                        .collect(Collectors.toList());
        return new ResponseEntity<>(answersResponse, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/questions/{questionId}")
    public ResponseEntity<AnswerResponse> saveAnswer(@RequestBody @Valid AnswerRequest answerRequest, @PathVariable Long userId, @PathVariable Long questionId) {
        var user = userService.getUserById(userId);
        var answer = answerMapper.toAnswerPOST(answerRequest);
        var savedAnswer = answerService.saveAnswer(answer, user, questionId);
        var answerResponse = answerMapper.fromAnswer(savedAnswer);
        return new ResponseEntity<>(answerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerResponse> updateAnswer(@RequestBody @Valid AnswerRequest answerRequest, @PathVariable Long answerId) {
        var answer = answerMapper.toAnswerPUT(answerRequest);
        var updatedAnswer = answerService.updateAnswer(answer, answerId);
        var answerResponse = answerMapper.fromAnswer(updatedAnswer);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerResponse> getAnswer(@PathVariable Long answerId) {
        var answer = answerService.getAnswerById(answerId);
        var answerResponse = answerMapper.fromAnswer(answer);
        return new ResponseEntity<>(answerResponse, HttpStatus.OK);
    }
}
