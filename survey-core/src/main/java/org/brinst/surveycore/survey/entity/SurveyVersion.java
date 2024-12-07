package org.brinst.surveycore.survey.entity;

import java.util.ArrayList;
import java.util.List;

import org.brinst.surveycore.survey.dto.SurveyItemDTO;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVersion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int version;
	@ManyToOne
	private Survey survey;
	@OneToMany(mappedBy = "surveyVersion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyQuestion> surveyQuestions = new ArrayList<>();

	public SurveyVersion(int version, List<SurveyItemDTO> items, Survey survey) {
		this.version = version;
		this.survey = survey;
		if (!CollectionUtils.isEmpty(items)) {
			this.surveyQuestions = items.stream().map(question
				-> new SurveyQuestion(question, this)).toList();
		}
	}
}
