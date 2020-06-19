package com.example.googleit.service;

import com.example.googleit.dto.request.AddTeamRequestDto;
import com.example.googleit.dto.request.SubmissionRequestDto;
import com.example.googleit.dto.response.*;
import com.example.googleit.entities.QuestionEntity;
import com.example.googleit.entities.SubmissionEntity;
import com.example.googleit.entities.TeamEntity;
import com.example.googleit.exceptions.ResourceNotFoundException;
import com.example.googleit.repository.QuestionRepository;
import com.example.googleit.repository.SubmissionRepository;
import com.example.googleit.repository.TeamRepository;
import com.example.googleit.utils.TeamScoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TeamScoreUtils teamScoreUtils;

    public ResponseDto addTeamToGame(AddTeamRequestDto addTeamRequestDto) {
        log.info("processing request for add team - {}", addTeamRequestDto);

        Optional<TeamEntity> optionalTeamEntity = teamRepository.findByTeamName(addTeamRequestDto.getTeamName());

        if (optionalTeamEntity.isPresent()) {
            log.info("the team name - {} is already taken", optionalTeamEntity.get().getTeamName());
            return new ResponseDto("400", "team name already taken", null);
        }

        optionalTeamEntity = teamRepository.findByEmail(addTeamRequestDto.getEmail());

        if (optionalTeamEntity.isPresent()) {
            log.info("the team with email id - {} already exists", optionalTeamEntity.get().getEmail());
            return new ResponseDto("400", "team email id exists", null);
        }

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setEmail(addTeamRequestDto.getEmail());
        teamEntity.setTeamName(addTeamRequestDto.getTeamName());
        teamEntity.setTeamName(addTeamRequestDto.getTeamName());
        teamEntity.setPassword(addTeamRequestDto.getPassword());
        teamEntity.setTeamScore(0);

        teamRepository.save(teamEntity);
        log.info("team successfully created for the request - {}", addTeamRequestDto);

        return new ResponseDto("200", "Success", null);
    }

    public ResponseDto addTeamSubmission(SubmissionRequestDto submissionRequestDto) {
        log.info("processing team submission request - {}", submissionRequestDto);

        Optional<TeamEntity> optionalTeamEntity = teamRepository.findByTeamName(submissionRequestDto.getTeamName());

        if (!optionalTeamEntity.isPresent()) {
            throw new ResourceNotFoundException("team name - " + submissionRequestDto.getTeamName() + " not found");
        }

        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(submissionRequestDto.getQuestionId());

        if (!optionalQuestionEntity.isPresent()) {
            throw new ResourceNotFoundException("question id - " + submissionRequestDto.getQuestionId() + " not found");
        }

        QuestionEntity questionEntity = optionalQuestionEntity.get();

        TeamEntity teamEntity = optionalTeamEntity.get();

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setQuestionEntity(questionEntity);
        submissionEntity.setTeamEntity(teamEntity);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("200");
        responseDto.setMessage("Success");

        if (submissionRequestDto.getAnswer().trim().equalsIgnoreCase(questionEntity.getImageAns())) {
            if (submissionRepository.getAcceptedSubmissions(questionEntity.getId(), teamEntity.getId()).getCount() == 0) {
                submissionEntity.setSubmissionStatus("ACCEPTED");
                submissionRepository.save(submissionEntity);
                SubmissionResponseSuccessDto submissionResponseSuccessDto =
                        teamScoreUtils.updateTeamScore(teamEntity.getTeamName(), questionEntity.getScore());
                responseDto.setData(submissionResponseSuccessDto);
            } else {
                submissionEntity.setSubmissionStatus("RESUBMISSION");
                submissionRepository.save(submissionEntity);
                SubmissionResponseSuccessDto submissionResponseSuccessDto = new SubmissionResponseSuccessDto();
                submissionResponseSuccessDto.setTeamScore(teamRepository.getTeamScore(teamEntity.getTeamName()));
                submissionResponseSuccessDto.setTeamSubmissionStatus("RESUBMISSION");
                responseDto.setData(submissionResponseSuccessDto);
                return responseDto;
            }
        } else {
            submissionEntity.setSubmissionStatus("WRONG");
            submissionRepository.save(submissionEntity);
            SubmissionResponseFailureDto submissionResponseFailureDto = new SubmissionResponseFailureDto();
            submissionResponseFailureDto.setTeamScore(teamEntity.getTeamScore());
            submissionResponseFailureDto.setTeamSubmissionStatus("Wrong");
            responseDto.setData(submissionResponseFailureDto);
        }
        return responseDto;
    }

    public ResponseDto getQuestionForTeam(Integer id) {
        QuestionEntity questionEntity = questionRepository.findById(id).get();
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setImageHint(questionEntity.getImageHint());
        questionResponseDto.setImageUrl(questionEntity.getImageUrl());
        questionResponseDto.setScore(questionEntity.getScore());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(questionResponseDto);
        responseDto.setMessage("Success");
        responseDto.setStatus("200");
        return responseDto;
    }

    public ResponseDto getLeaderboard() {
        List<LeaderboardDto> leaderboardDtoList = new ArrayList<>();
        leaderboardDtoList = teamRepository.getLeaderBoardResults().stream()
                .map(teamEntity -> {
                    LeaderboardDto leaderboardDto = new LeaderboardDto();
                    leaderboardDto.setScore(teamEntity.getTeamScore());
                    leaderboardDto.setTeamName(teamEntity.getTeamName());
                    return leaderboardDto;
                }).collect(Collectors.toList());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(leaderboardDtoList);
        responseDto.setStatus("200");
        responseDto.setMessage("Success");
        return responseDto;
    }
}
