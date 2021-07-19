package br.com.askufcg.controller;

import br.com.askufcg.models.Comment;
import br.com.askufcg.serviceImpl.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public Comment addComment(@RequestBody Comment comment) {
        return this.commentService.addComment(comment);
    }
}
