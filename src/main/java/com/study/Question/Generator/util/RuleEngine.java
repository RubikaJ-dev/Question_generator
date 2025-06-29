package com.study.Question.Generator.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class RuleEngine {

    private Map<String, Map<String, List<Map<String, String>>>> templateMap;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/static/questions_templates.json");
        templateMap = mapper.readValue(is, new TypeReference<>() {});
    }

    public String getTemplate(String taxonomy, String level) {
        taxonomy = taxonomy.toLowerCase();
        level = level.toLowerCase();

        if (!templateMap.containsKey(taxonomy)) {
            throw new IllegalArgumentException("Invalid taxonomy: " + taxonomy);
        }

        Map<String, List<Map<String, String>>> levelMap = templateMap.get(taxonomy);
        if (!levelMap.containsKey(level)) {
            throw new IllegalArgumentException("Invalid level: " + level + " for taxonomy: " + taxonomy);
        }

        List<Map<String, String>> templates = levelMap.get(level);
        if (templates == null || templates.isEmpty()) {
            throw new IllegalStateException("No templates found for " + taxonomy + " -> " + level);
        }

        List<String> questions = templates.stream()
                .map(t -> t.get("template"))
                .filter(q -> q != null && !q.isBlank())
                .toList();

        if (questions.isEmpty()) {
            throw new IllegalStateException("Templates found but no valid questions for " + taxonomy + " -> " + level);
        }

        return questions.get(new Random().nextInt(questions.size()));
    }

}
