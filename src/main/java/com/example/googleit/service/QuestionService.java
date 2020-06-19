package com.example.googleit.service;

import com.example.googleit.dto.request.AddQuestionRequestDto;
import com.example.googleit.dto.response.ResponseDto;
import com.example.googleit.entities.QuestionEntity;
import com.example.googleit.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseDto addQuestion(AddQuestionRequestDto addQuestionRequestDto) {

        ResponseDto responseDto = new ResponseDto();

        Optional<QuestionEntity> optionalQuestionEntity =
                questionRepository.findByImageAns(addQuestionRequestDto.getImageAns());
        Optional<QuestionEntity> entityOptional =
                questionRepository.findByImageUrl(addQuestionRequestDto.getImageUrl());

        if (optionalQuestionEntity.isPresent() || entityOptional.isPresent()) {
            responseDto.setData(null);
            responseDto.setMessage("question already exists in the database");
            responseDto.setStatus("400");
            return responseDto;
        }

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setImageAns(addQuestionRequestDto.getImageAns());
        questionEntity.setImageHint(addQuestionRequestDto.getImageHint());
        questionEntity.setScore(addQuestionRequestDto.getScore());
        questionEntity.setImageUrl(addQuestionRequestDto.getImageUrl());

        questionRepository.save(questionEntity);

        responseDto.setData(null);
        responseDto.setMessage("question added to the database");
        responseDto.setStatus("200");

        return null;
    }
}
