package com.example.googleit.entities;

import com.example.googleit.dto.response.LeaderboardDto;
import com.example.googleit.dto.response.SubmissionCountDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "submission")

@SqlResultSetMapping(
        name = "getNumberOfTimesOfSubmission",
        classes = @ConstructorResult(
                targetClass = SubmissionCountDto.class,
                columns = {
                        @ColumnResult(name = "count", type = Long.class)
                }
        )
)

@org.hibernate.annotations.NamedNativeQuery(
        name = "getNumberOfTimesOfSubmission",
        query = "SELECT count(*) as count FROM submission WHERE submission_status='ACCEPTED' " +
                " AND question_id= :questionId AND team_id= :teamId ;",
        resultSetMapping = "getNumberOfTimesOfSubmission",
        readOnly = true,
        resultClass = SubmissionCountDto.class)

@Data
@NoArgsConstructor
public class SubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;


    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private TeamEntity teamEntity;

    @Column
    private String submissionStatus;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime timestamp;
}
