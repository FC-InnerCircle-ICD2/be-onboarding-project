package com.fastcampus.survey.util.mapper;

import com.fastcampus.survey.dto.QuestionDto;
import com.fastcampus.survey.dto.SurveyDto;
import com.fastcampus.survey.entity.Question;
import com.fastcampus.survey.entity.Survey;
import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;

import java.util.ArrayList;
import java.util.List;

public class SurveyMapper {
    // DTO -> Entity 변환
    public static Survey toEntity(SurveyDto dto) {
        List<Question> questions = dto.getQuestions().stream().map(QuestionMapper::toEntity).toList();
        return Survey.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .questions(questions)
                .build();
    }

    // Entity -> DTO 변환
    public static SurveyDto toDto(Survey entity) {
        List<QuestionDto> questionDtos = entity.getQuestions().stream().map(QuestionMapper::toDto).toList();
        return SurveyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .questions(questionDtos)
                .build();
    }
}
