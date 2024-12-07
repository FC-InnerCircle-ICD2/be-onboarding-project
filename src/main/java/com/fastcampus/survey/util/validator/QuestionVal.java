package com.fastcampus.survey.util.validator;

import com.fastcampus.survey.dto.QuestionDto;
import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;
import com.fastcampus.survey.util.exception.exception.ValidException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class QuestionVal {

    public static void validateQuestion(QuestionDto questionDto) {
        validQType(questionDto.getQType());
        validChoices(questionDto.getQType(), questionDto.getChoices());
        validQMust(questionDto.getQMust());
    }

    private static void validQType(String value) {
        try {
            AnsType.fromValue(value);
        } catch (Exception e) {
            throw new ValidException("올바르지 않은 입력 형태입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validChoices(String ansTypeValue, List<String> value) {
        AnsType ansType = null;
        try {
            ansType = AnsType.fromValue(ansTypeValue);
        } catch (Exception e) {
            throw new ValidException("올바르지 않은 입력 형태입니다.", HttpStatus.BAD_REQUEST);
        }
        if ( (AnsType.LIST_CHOOSE_ONE.equals(ansType) || AnsType.LIST_CHOOSE_MULTI.equals(ansType)) &&  // 항목 입력 형태가 리스트 단일 선택 or 리스트 복수 선택일때
             (value == null || value.isEmpty())) { // 선택지가 없을 때
            throw new ValidException("선택지를 1개 이상 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validQMust(String value) {
        try {
            Must.fromValue(value);
        } catch (Exception e) {
            throw new ValidException("올바르지 않은 필수 여부 입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
