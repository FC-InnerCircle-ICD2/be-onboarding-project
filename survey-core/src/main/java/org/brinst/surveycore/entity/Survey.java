package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;

import org.brinst.surveycommon.dto.SurveyDTO;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Survey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
	private List<SurveyQuestion> surveyQuestions = new ArrayList<>();

	public Survey(SurveyDTO.ReqDTO surveyReqDTO) {
		if (surveyReqDTO != null) {
			this.name = surveyReqDTO.getName();
			this.description = surveyReqDTO.getDescription();
			if (!CollectionUtils.isEmpty(surveyReqDTO.getItemList())) {
				this.surveyQuestions.addAll(surveyReqDTO.getItemList().stream().map(SurveyQuestion::new).toList());
			}
		}
	}
}
