package br.com.askufcg.controllers;

import br.com.askufcg.dtos.question.QuestionMapper;
import br.com.askufcg.dtos.question.QuestionResponse;
import br.com.askufcg.services.search.QuestionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class QuestionSearchController {
    @Autowired
    private QuestionSearchService questionSearchService;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAll(@RequestParam(defaultValue = "") String title,
                                                         @RequestParam(required = false) Set<String> tags,
                                                         @RequestParam(defaultValue = "new") String filter) {
        var questions = questionSearchService.search(title, tags, filter.toLowerCase()).stream()
                                                                                     .map(question -> questionMapper.fromQuestion(question))
                                                                                     .collect(Collectors.toList());
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
