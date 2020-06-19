package com.example.googleit.controller;

import com.example.googleit.dto.request.AddQuestionRequestDto;
import com.example.googleit.dto.response.ResponseDto;
import com.example.googleit.service.QuestionService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/add/question")
    public ResponseDto addQuestion(@RequestBody @NonNull @Valid AddQuestionRequestDto requestDto) {
        return questionService.addQuestion(requestDto);
    }
}
