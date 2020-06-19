package com.example.googleit.repository;

import com.example.googleit.dto.response.SubmissionCountDto;
import com.example.googleit.entities.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Integer> {

    @Query(name = "getNumberOfTimesOfSubmission", nativeQuery = true)
    SubmissionCountDto getAcceptedSubmissions(@Param("questionId") Integer questionId,
                                              @Param("teamId") Integer teamId);
}

