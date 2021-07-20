package br.com.askufcg.controllers;

import br.com.askufcg.dtos.PostCommentDTO;
import br.com.askufcg.models.Comment;
import br.com.askufcg.services.comment.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/answers/{answerId}/users/{userId}")
    public Comment addCommentAnswer(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId, @PathVariable("answerId") Long answerId){
        return this.commentService.addCommentAnswer(comment, userId, answerId);
    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public Comment addCommentQuestion(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId, @PathVariable("questionId") Long questionId){
        return this.commentService.addCommentQuestion(comment, userId, questionId);
    }
    @GetMapping("{commentId}/answers/{answerId}")
    public Comment exhibitCommentAnswer(@PathVariable("commentId") Long commentId, @PathVariable("answerId") Long answerId){
        return this.commentService.exhibitCommentAnswer(commentId, answerId);
    }

    @GetMapping("/answers/{answerId}")
    public List<Comment> exhibitAllCommentsAnswer(@PathVariable("answerId") Long answerId){
        return this.commentService.exhibitAllCommentsAnswer(answerId);
    }
}
