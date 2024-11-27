package org.brinst.surveycore.entity;

import java.util.ArrayList;
import java.util.List;

import org.brinst.surveycommon.dto.SurveyDTO;

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
	private int version;
	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyVersion> surveyVersions = new ArrayList<>();

	public Survey(SurveyDTO.ReqDTO surveyReqDTO) {
		if (surveyReqDTO != null) {
			this.name = surveyReqDTO.getName();
			this.description = surveyReqDTO.getDescription();
			this.version = addVersion();
			this.surveyVersions.add(new SurveyVersion(this.version, surveyReqDTO.getItemList(), this));
		}
	}

	public void modifySurvey(SurveyDTO.ReqDTO surveyReqDTO) {
		if (surveyReqDTO != null) {
			this.name = surveyReqDTO.getName();
			this.description = surveyReqDTO.getDescription();
			this.version = addVersion();
			this.surveyVersions.add(new SurveyVersion(this.version, surveyReqDTO.getItemList(), this));
		}
	}

	private int addVersion() {
		return this.version + 1;
	}
}
