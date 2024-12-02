package org.brinst.surveycore.survey.entity;

import java.util.ArrayList;
import java.util.List;

import org.brinst.surveycommon.enums.OptionType;
import org.brinst.surveycore.answer.entity.AnswerParent;
import org.brinst.surveycore.survey.dto.SurveyItemDTO;
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
	@OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnswerParent> answerItems = new ArrayList<>();
	@ManyToOne
	private SurveyVersion surveyVersion;
	@OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyOption> surveyOptions = new ArrayList<>();

	public SurveyQuestion(SurveyItemDTO itemDTO, SurveyVersion surveyVersion) {
		this.name = itemDTO.getName();
		this.description = itemDTO.getDescription();
		this.optionType = itemDTO.getOptionType();
		this.required = itemDTO.isRequired();
		this.surveyVersion = surveyVersion;

		if (createYNByType(optionType)) {
			addSurveyOptions(itemDTO.getOptions());
		}
	}

	public Object getOption() {
		return switch (this.optionType) {
			case SHORT_ANSWER, LONG_ANSWER -> null;
			case SINGLE_CHOICE -> this.surveyOptions.get(0).getOption();
			case MULTIPLE_CHOICE -> this.surveyOptions.stream().map(SurveyOption::getOption).toList();
		};
	}

	private boolean createYNByType(OptionType optionType) {
		return optionType == OptionType.SINGLE_CHOICE || optionType == OptionType.MULTIPLE_CHOICE;
	}

	private void addSurveyOptions(List<String> options) {
		if (!CollectionUtils.isEmpty(options)) {
			this.surveyOptions.addAll(
				options.stream()
					.map(option -> new SurveyOption(option, this))
					.toList()
			);
		}
	}
}
