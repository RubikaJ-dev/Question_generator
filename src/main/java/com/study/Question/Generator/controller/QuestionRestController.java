package com.study.Question.Generator.controller;

import com.study.Question.Generator.entity.GeneratedQuestions;
import com.study.Question.Generator.model.QuestionRequest;
import com.study.Question.Generator.model.QuestionResponse;
import com.study.Question.Generator.service.QuestionGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/questions")
public class QuestionRestController {

    @Autowired
    private QuestionGeneratorService generatorService;
    @PostMapping
    public GeneratedQuestions createQuestion(@RequestBody GeneratedQuestions question) {
        return generatorService.saveQuestion(question);
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> generate(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(generatorService.generateQuestion(request));
    }
}
