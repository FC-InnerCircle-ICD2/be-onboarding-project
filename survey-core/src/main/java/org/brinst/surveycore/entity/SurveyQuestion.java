package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;

import org.brinst.surveycommon.dto.SurveyDTO;
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
	private long version;
	private String name;
	private String description;
	private boolean required;
	@Enumerated(EnumType.STRING)
	private OptionType optionType;
	@ManyToOne
	private Survey survey;
	@OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL)
	private List<SurveyOption> options = new ArrayList<>();

	public SurveyQuestion(SurveyDTO.ReqItemDTO reqItemDTO) {
		this.name = reqItemDTO.getName();
		this.description = reqItemDTO.getDescription();
		this.optionType = reqItemDTO.getType();
		if (!CollectionUtils.isEmpty(reqItemDTO.getOptions())) {
			this.options.addAll(reqItemDTO.getOptions().stream().map(SurveyOption::new).toList());
		}
	}
}
