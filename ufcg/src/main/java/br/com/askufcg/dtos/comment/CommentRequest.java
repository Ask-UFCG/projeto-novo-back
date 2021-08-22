package br.com.askufcg.dtos.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentRequest {
    @NotEmpty(message = "O comentário não pode ser vazio.")
    private String content;
}
