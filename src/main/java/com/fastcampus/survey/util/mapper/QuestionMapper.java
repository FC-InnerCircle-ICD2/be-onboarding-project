package com.fastcampus.survey.util.mapper;

import com.fastcampus.survey.dto.QuestionDto;
import com.fastcampus.survey.entity.Question;
import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;

public class QuestionMapper {
    // DTO -> Entity 변환
    public static Question toEntity(QuestionDto dto) {
        try {
            AnsType ansType = AnsType.fromValue(dto.getQType());
            return Question.builder()
                    .id(dto.getId())
                    .qName(dto.getQName())
                    .qDescription(dto.getQDescription())
                    .qType(ansType)
                    .choices(AnsType.SHORT.equals(ansType) || AnsType.LONG.equals(ansType) ? null : dto.getChoices()) // 단답 또는 장문일 경우 null, 그외엔 선택 리스트
                    .qMust(Must.fromValue(dto.getQMust()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Entity -> DTO 변환
    public static QuestionDto toDto(Question entity) {
        return QuestionDto.builder()
                .id(entity.getId())
                .qName(entity.getQName())
                .qDescription(entity.getQDescription())
                .qType(entity.getQType().getAnsType())
                .choices(entity.getChoices())
                .qMust(entity.getQMust().getFlg())
                .build();
    }
}
