package org.brinst.surveycore.survey.entity;

import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.exception.GlobalException;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SurveyOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String option;
	@ManyToOne
	private SurveyQuestion surveyQuestion;

	public SurveyOption(String option, SurveyQuestion surveyQuestion) {
		validationEmptyOption(option);
		this.option = option;
		this.surveyQuestion = surveyQuestion;
	}

	private void validationEmptyOption(String option) {
		if (StringUtils.isEmpty(option) ) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "옵션 값은 빈 값을 입력할 수 없습니다.");
		}
	}
}
