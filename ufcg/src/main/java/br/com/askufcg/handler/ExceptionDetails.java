package br.com.askufcg.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDetails {
    private int status;
    private String message;
}
