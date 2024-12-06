package com.fastcampus.survey.util.validator;

import com.fastcampus.survey.dto.QuestionDto;
import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;

import java.util.List;

public class QuestionVal {

    public static void validateQuestion(QuestionDto questionDto) {
        validQType(questionDto.getQType());
        validChoices(AnsType.valueOf(questionDto.getQType()), questionDto.getChoices());
        validQMust(questionDto.getQMust());
    }

    private static void validQType(String value) {
        try {
            AnsType.fromValue(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void validChoices(AnsType ansType, List<String> value) {
        if ( (AnsType.LIST_CHOOSE_ONE.equals(ansType) || AnsType.LIST_CHOOSE_MULTI.equals(ansType)) &&  // 항목 입력 형태가 리스트 단일 선택 or 리스트 복수 선택일때
             (value.size() == 0 || value.isEmpty())) { // 선택지가 없을 때
            throw new RuntimeException(new Exception());
        }
    }

    private static void validQMust(String value) {
        try {
            Must.fromValue(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
