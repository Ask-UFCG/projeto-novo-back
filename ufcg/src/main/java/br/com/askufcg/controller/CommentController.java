package br.com.askufcg.controller;

import br.com.askufcg.dto.PostCommentDTO;
import br.com.askufcg.models.Comment;
import br.com.askufcg.serviceImpl.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/answer/{userId}/{answerId}")
    public Comment addCommentAnswer(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId, @PathVariable("answerId") Long answerid){
        return this.commentService.addCommentAnswer(comment, userId, answerid);
    }

    @PostMapping("/question/{userId}/{questionId}")
    public Comment addCommentQuestion(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId, @PathVariable("questionId") Long questionId){
        return this.commentService.addCommentQuestion(comment, userId, questionId);
    }
}
