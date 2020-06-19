package com.example.googleit.repository;

import com.example.googleit.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    Optional<TeamEntity> findByTeamName(String teamName);

    Optional<TeamEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE team SET team_score= :teamScore WHERE team_name = :teamName",
            nativeQuery = true)
    void updateTeamScore(@Param("teamScore") int teamScore, @Param("teamName") String teamName);


    @Query(value = "SELECT team_score from team WHERE team_name = :teamName",
            nativeQuery = true)
    Integer getTeamScore(@Param("teamName") String teamName);

    @Query(value = "SELECT * FROM team ORDER BY team_score DESC;", nativeQuery = true)
    List<TeamEntity> getLeaderBoardResults();
}
