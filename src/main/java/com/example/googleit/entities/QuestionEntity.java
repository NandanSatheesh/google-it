package com.example.googleit.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String imageUrl;

    @Column(unique = true)
    private String imageAns;

    @Column
    private String imageHint;

    @Column(columnDefinition = "integer default 0")
    private Integer score;
}
