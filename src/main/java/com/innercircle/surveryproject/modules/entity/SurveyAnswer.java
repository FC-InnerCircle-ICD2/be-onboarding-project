package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.dto.SurveyAnswerCreateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 설문조사 응답
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyAnswer extends BaseEntity {

    /**
     * 설문조사 ID
     */
    @EmbeddedId
    private SurveyAnswerId surveyAnswerId;

    /**
     * 사용자 이름
     */
    private String username;

    /**
     * 설문조사 응답 결과
     */
    @ElementCollection
    @CollectionTable(
        name = "survey_answer_survey_answer_map",
        joinColumns = {
            @JoinColumn(name = "survey_id", referencedColumnName = "surveyId"),
            @JoinColumn(name = "phone_number", referencedColumnName = "phoneNumber")
        }
    )
    @MapKeyColumn(name = "survey_answer_map_key") // 맵의 키를 컬럼으로 지정
    private Map<Long, String> surveyAnswerMap;

    public SurveyAnswer(SurveyAnswerCreateDto surveyCreateDto, Map<Long, String> surveyAnswerMap) {
        this.surveyAnswerId = SurveyAnswerId.of(surveyCreateDto.getSurveyId(), surveyCreateDto.getPhoneNumber());
        this.username = surveyCreateDto.getUsername();
        this.surveyAnswerMap = surveyAnswerMap;
    }

    public static SurveyAnswer from(SurveyAnswerCreateDto surveyCreateDto, Map<Long, String> surveyAnswerMap) {
        return new SurveyAnswer(surveyCreateDto, surveyAnswerMap);
    }

    public Long getSurveyId() {
        return this.surveyAnswerId.getSurveyId();
    }

    public Long getPhoneNumber() {
        return this.surveyAnswerId.getPhoneNumber();
    }

}
