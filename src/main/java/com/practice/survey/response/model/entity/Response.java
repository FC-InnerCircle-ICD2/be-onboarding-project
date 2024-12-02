package com.practice.survey.response.model.entity;

import com.practice.survey.common.model.entity.BaseTime;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Response<S> extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id", columnDefinition = "BIGINT")
    private Long responseId;

    @CreationTimestamp
    @Column(name="submitted_at",nullable = false,updatable = false)
    private LocalDateTime submittedAt;

    private String respondentId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "version_id", nullable = false)
    private SurveyVersion version;

}
