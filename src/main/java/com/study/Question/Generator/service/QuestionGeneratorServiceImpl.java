package com.study.Question.Generator.service;

import com.study.Question.Generator.Repository.GeneratedQuestionRepository;
import com.study.Question.Generator.entity.GeneratedQuestions;
import com.study.Question.Generator.model.QuestionRequest;
import com.study.Question.Generator.model.QuestionResponse;
import com.study.Question.Generator.util.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionGeneratorServiceImpl implements QuestionGeneratorService{

    @Autowired
    private GeneratedQuestionRepository repository;

    public GeneratedQuestions saveQuestion(GeneratedQuestions question) {
        return repository.save(question);
    }

    @Autowired
    private RuleEngine ruleEngine;
    private String fillTemplate(String template, Map<String, String> conceptMap) {
        for (Map.Entry<String, String> entry : conceptMap.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return template;
    }

    @Override
    public QuestionResponse generateQuestion(QuestionRequest request) {
        // 1. Get a random template from RuleEngine
        String template = ruleEngine.getTemplate(request.getTaxonomy(), request.getLevel());

        // 2. Extract concepts from the pasted content
        Map<String, String> conceptMap = extractConcepts(request.getContent());

        // 3. Fill the template dynamically with extracted data
        String question = fillTemplate(template, conceptMap);

        return new QuestionResponse(question);
    }

    public Map<String, String> extractConcepts(String content) {
        String[] words = content.split("\\s+");

        Map<String, String> conceptMap = new HashMap<>();
        conceptMap.put("concept", words.length > 0 ? words[0] : "concept");
        conceptMap.put("concept1", words.length > 1 ? words[1] : "concept1");
        conceptMap.put("concept2", words.length > 2 ? words[2] : "concept2");
        conceptMap.put("problem", "climate change");
        conceptMap.put("alternative", "Western Ghats");
        conceptMap.put("tool", "forecast model");
        conceptMap.put("purpose", "rain prediction");
        conceptMap.put("task", "analysis");
        conceptMap.put("skill1", "map reading");
        conceptMap.put("skill2", "data interpretation");
        conceptMap.put("goal", "exam preparation");

        return conceptMap;
    }

}
