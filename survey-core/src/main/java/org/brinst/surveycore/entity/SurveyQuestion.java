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
	private String name;
	private String description;
	private boolean required;
	@Enumerated(EnumType.STRING)
	private OptionType optionType;
	@ManyToOne
	private SurveyVersion surveyVersion;
	@OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyOption> surveyOptions = new ArrayList<>();

	public SurveyQuestion(SurveyDTO.ItemDTO itemDTO, SurveyVersion surveyVersion) {
		this.name = itemDTO.getName();
		this.description = itemDTO.getDescription();
		this.optionType = itemDTO.getType();
		this.surveyVersion = surveyVersion;
		if (!CollectionUtils.isEmpty(itemDTO.getOptions())) {
			this.surveyOptions.addAll(itemDTO.getOptions().stream().map(option
				-> new SurveyOption(option, this)).toList());
		}
	}
}
