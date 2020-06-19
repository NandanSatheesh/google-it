package com.example.googleit.controller;

import com.example.googleit.dto.request.AddTeamRequestDto;
import com.example.googleit.dto.request.SubmissionRequestDto;
import com.example.googleit.dto.response.ResponseDto;
import com.example.googleit.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/add/team")
    public ResponseDto addTeam(@RequestBody @NotNull @Valid AddTeamRequestDto addTeamRequestDto) {
        return teamService.addTeamToGame(addTeamRequestDto);
    }

    @PostMapping("/add/team/submission")
    public ResponseDto addTeamSubmission(@RequestBody @NotNull @Valid SubmissionRequestDto submissionRequestDto) {
        return teamService.addTeamSubmission(submissionRequestDto);
    }

    @GetMapping("/team/question/{questionId}")
    public ResponseDto getQuestion(@PathVariable("questionId") Integer questionId) {
        return teamService.getQuestionForTeam(questionId);
    }

    @GetMapping("/leaderboard")
    public ResponseDto getLeaderboard() {
        return teamService.getLeaderboard();
    }
}
