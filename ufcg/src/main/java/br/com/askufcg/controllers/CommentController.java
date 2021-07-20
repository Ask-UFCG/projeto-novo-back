package br.com.askufcg.controllers;

import br.com.askufcg.dtos.PostCommentDTO;
import br.com.askufcg.models.Comment;
import br.com.askufcg.services.comment.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/answers/{answerId}/users/{userId}")
    public Comment addCommentAnswer(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId, @PathVariable("answerId") Long answerid){
        return this.commentService.addCommentAnswer(comment, userId, answerid);
    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public Comment addCommentQuestion(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId, @PathVariable("questionId") Long questionId){
        return this.commentService.addCommentQuestion(comment, userId, questionId);
    }
}
