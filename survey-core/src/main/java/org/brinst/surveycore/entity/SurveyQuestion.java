package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.brinst.surveycommon.config.GlobalException;
import org.brinst.surveycommon.dto.SurveyDTO;
import org.brinst.surveycommon.enums.ErrorCode;
import org.brinst.surveycommon.enums.OptionType;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private boolean required;
	@Enumerated(EnumType.STRING)
	private OptionType optionType;
	@ManyToOne
	private SurveyVersion surveyVersion;
	@OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyOption> surveyOptions = new ArrayList<>();
	@OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnswerItem> answerItems = new ArrayList<>();

	public SurveyQuestion(SurveyDTO.ItemDTO itemDTO, SurveyVersion surveyVersion) {
		this.name = itemDTO.getName();
		this.description = itemDTO.getDescription();
		this.optionType = itemDTO.getType();
		this.required = itemDTO.isRequired();
		this.surveyVersion = surveyVersion;
		if (!CollectionUtils.isEmpty(itemDTO.getOptions())) {
			this.surveyOptions.addAll(itemDTO.getOptions().stream().map(option
				-> new SurveyOption(option, this)).toList());
		}
	}

	/**
	 * 필수값 여부를 체크하는 메소드
	 *
	 * @param answers
	 */
	public void validateRequiredAnswer(List<String> answers) {
		if (this.required && CollectionUtils.isEmpty(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST);
		}
	}

	public void validateAnswerByOptionType(List<String> answers) {
		if (this.optionType != OptionType.MULTIPLE_CHOICE && answers.size() > 1) {
			throw new GlobalException(ErrorCode.BAD_REQUEST);
		}
	}

	public void validateAnswerByChoice(List<String> answers) {
		Set<String> validOptions = this.surveyOptions.stream()
			.map(SurveyOption::getOption)
			.collect(Collectors.toSet());

		// 필수값 검증
		if (this.required && CollectionUtils.isEmpty(answers)) {
			throw new GlobalException(ErrorCode.BAD_REQUEST, "Answer is required.");
		}

		// 단일 선택 검증
		if (this.optionType == OptionType.SINGLE_CHOICE) {
			if (answers.size() != 1 || !validOptions.contains(answers.get(0))) {
				throw new IllegalArgumentException("Invalid single choice answer.");
			}
		}

		// 다중 선택 검증
		if (this.optionType == OptionType.MULTIPLE_CHOICE) {
			if ((answers.isEmpty() || !validOptions.containsAll(answers))) {
				throw new IllegalArgumentException("Invalid multiple choice answers.");
			}
		}
	}
}
