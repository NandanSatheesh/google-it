package com.example.googleit.utils;

import com.example.googleit.dto.response.SubmissionResponseSuccessDto;
import com.example.googleit.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamScoreUtils {

    @Autowired
    private TeamRepository teamRepository;

    public SubmissionResponseSuccessDto updateTeamScore(String teamName, int score) {
        Integer teamScore = teamRepository.getTeamScore(teamName);
        teamRepository.updateTeamScore(teamScore + score, teamName);
        SubmissionResponseSuccessDto submissionResponseSuccessDto = new SubmissionResponseSuccessDto();
        submissionResponseSuccessDto.setTeamScore(teamScore + score);
        submissionResponseSuccessDto.setTeamSubmissionStatus("ACCEPTED");
        return submissionResponseSuccessDto;
    }
}
