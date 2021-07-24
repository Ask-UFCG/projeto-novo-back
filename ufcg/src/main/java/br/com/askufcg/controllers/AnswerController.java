package br.com.askufcg.controllers;

import br.com.askufcg.models.Answer;
import br.com.askufcg.services.answer.AnswerService;
import br.com.askufcg.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
@CrossOrigin
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Answer>> getUserAnswers(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return new ResponseEntity<>(answerService.getUserAnswers(user), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/questions/{questionId}")
    public ResponseEntity<Answer> saveAnswer(@RequestBody Answer answer, @PathVariable Long userId, @PathVariable Long questionId) {
        var user = userService.getUserById(userId);
        return new ResponseEntity<>(answerService.saveAnswer(answer, user, questionId), HttpStatus.CREATED);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<Answer> updateAnswer(@RequestBody Answer answer, @PathVariable Long answerId) {
        return new ResponseEntity<>(answerService.updateAnswer(answer, answerId), HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> updateAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<Answer> getAnswer(@PathVariable Long answerId) {
        return new ResponseEntity<>(answerService.getAnswerById(answerId), HttpStatus.OK);
    }
}
