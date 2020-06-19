package com.example.googleit.repository;

import com.example.googleit.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

    Optional<QuestionEntity> findByImageAns(String imageAns);

    Optional<QuestionEntity> findByImageUrl(String imageAns);
}
