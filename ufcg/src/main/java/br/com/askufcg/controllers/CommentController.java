package br.com.askufcg.controllers;

import br.com.askufcg.dtos.answer.AnswerResponse;
import br.com.askufcg.dtos.comment.CommentMapper;
import br.com.askufcg.dtos.comment.CommentRequest;
import br.com.askufcg.dtos.comment.CommentResponse;
import br.com.askufcg.models.Answer;
import br.com.askufcg.models.Comment;
import br.com.askufcg.models.User;
import br.com.askufcg.services.comment.CommentService;
import br.com.askufcg.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/answers/{answerId}/users/{userId}")
    public ResponseEntity<CommentResponse> addCommentAnswer(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable Long answerId){
        Comment comment = commentMapper.toCommentPOST(commentRequest);
        Comment commentResult = this.commentService.addCommentAnswer(comment, userId, answerId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @PostMapping("/questions/{questionId}/users/{userId}")
    public  ResponseEntity<CommentResponse> addCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long userId, @PathVariable     Long questionId){
        Comment comment = commentMapper.toCommentPOST(commentRequest);
        Comment commentResult = this.commentService.addCommentQuestion(comment, userId, questionId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }
    @GetMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> getCommentAnswer(@PathVariable Long commentId, @PathVariable Long answerId){
        Comment commentResult = this.commentService.getCommentAnswer(commentId, answerId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsAnswer(@PathVariable Long answerId){
        List<Comment> comments = this.commentService.getAllCommentsAnswer(answerId);
        List<CommentResponse> commentsResponse = comments.stream()
                .map(comment -> commentMapper.fromComment(comment))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);

    }

    @GetMapping("{commentId}/questions/{questionId}")
    public ResponseEntity<CommentResponse> getCommentQuestion(@PathVariable Long commentId, @PathVariable   Long questionId){
        Comment commentResult = this.commentService.getCommentQuestion(commentId, questionId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsQuestion(@PathVariable    Long questionId){
        List<Comment> comments = this.commentService.getAllCommentsQuestion(questionId);
        List<CommentResponse> commentsResponse = comments.stream()
                .map(comment -> commentMapper.fromComment(comment))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse,HttpStatus.OK);
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
    public ResponseEntity<CommentResponse> updateCommentQuestion(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId, @PathVariable   Long questionId){
        Comment comment = this.commentMapper.toCommentPUT(commentRequest);
        Comment commentResult = this.commentService.updateCommentQuestion(comment, commentId, questionId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @PutMapping("{commentId}/answers/{answerId}")
    public ResponseEntity<CommentResponse> updateCommentAnswer(@RequestBody CommentRequest commentRequest, @PathVariable Long commentId, @PathVariable Long answerId){
        Comment comment = this.commentMapper.toCommentPUT(commentRequest);
        Comment commentResult = this.commentService.updateCommentAnswer(comment, commentId, answerId);
        CommentResponse commentResponse = commentMapper.fromComment(commentResult);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }
}
