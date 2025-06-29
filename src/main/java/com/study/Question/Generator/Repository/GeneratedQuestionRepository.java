package com.study.Question.Generator.Repository;

import com.study.Question.Generator.entity.GeneratedQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedQuestionRepository extends JpaRepository<GeneratedQuestions,Long> {
}
