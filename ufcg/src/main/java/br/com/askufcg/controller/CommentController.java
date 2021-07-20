package br.com.askufcg.controller;

import br.com.askufcg.dto.PostCommentDTO;
import br.com.askufcg.models.Comment;
import br.com.askufcg.serviceImpl.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{userId}")
    public Comment addComment(@RequestBody PostCommentDTO comment, @PathVariable("userId") Long userId){
        return this.commentService.addComment(comment, userId);
    }
}
