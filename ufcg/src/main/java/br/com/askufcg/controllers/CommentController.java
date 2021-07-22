package br.com.askufcg.controllers;

import br.com.askufcg.dtos.comment.PostCommentDTO;
import br.com.askufcg.models.Comment;
import br.com.askufcg.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {
    @Autowired
    private  CommentService commentService;

    @PostMapping("/answers/{answerId}/users/{userId}")
    public ResponseEntity<Comment> addCommentAnswer(@RequestBody PostCommentDTO comment, @PathVariable Long userId, @PathVariable Long answerId){
        Comment commentResult = this.commentService.addCommentAnswer(comment, userId, answerId);
        return new ResponseEntity<>(commentResult, HttpStatus.CREATED);
    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public  ResponseEntity<Comment> addCommentQuestion(@RequestBody PostCommentDTO comment, @PathVariable Long userId, @PathVariable     Long questionId){
        Comment commentResult = this.commentService.addCommentQuestion(comment, userId, questionId);;
        return new ResponseEntity<>(commentResult, HttpStatus.CREATED);
    }
    @GetMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<Comment> exhibitCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        Comment commentResult = this.commentService.exhibitCommentAnswer(commentId, answerId);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<List<Comment>> exhibitAllCommentsAnswer(@PathVariable Long answerId){
        List<Comment> comments = this.commentService.exhibitAllCommentsAnswer(answerId);
        return new ResponseEntity<>(comments,HttpStatus.OK);

    }

    @GetMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<Comment> exhibitCommentQuestion(@PathVariable Long commentId, @PathVariable   Long questionId){
        Comment commentResult = this.commentService.exhibitCommentQuestion(commentId, questionId);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<List<Comment>> exhibitAllCommentsQuestion(@PathVariable    Long questionId){
        List<Comment> comments = this.commentService.exhibitAllCommentsQuestion(questionId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    @DeleteMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<Comment> deleteCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        Comment commentResult = this.commentService.deleteCommentAnswer(commentId, answerId);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @DeleteMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<Comment> deleteCommentQuestion(@PathVariable Long commentId, @PathVariable    Long questionId){
        Comment commentResult = this.commentService.deleteCommentQuestion(commentId, questionId);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @PutMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<Comment> updateCommentQuestion(@RequestBody PostCommentDTO comment, @PathVariable Long commentId, @PathVariable   Long questionId){
        Comment commentResult = this.commentService.updateCommentQuestion(comment, commentId, questionId);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }

    @PutMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<Comment> updateCommentAnswer(@RequestBody PostCommentDTO commentDTO, @PathVariable Long commentId, @PathVariable Long answerId){
        Comment commentResult = this.commentService.updateCommentAnswer(commentDTO, commentId, answerId);
        return new ResponseEntity<>(commentResult, HttpStatus.OK);
    }
}
