package com.study.Question.Generator.service;

import com.study.Question.Generator.entity.GeneratedQuestions;
import com.study.Question.Generator.model.QuestionRequest;
import com.study.Question.Generator.model.QuestionResponse;

import java.util.Map;

public interface QuestionGeneratorService {

    public QuestionResponse generateQuestion(QuestionRequest request);
    public GeneratedQuestions saveQuestion(GeneratedQuestions questions);

    public Map<String, String> extractConcepts(String content);
}
