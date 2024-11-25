package com.onboarding.servey.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onboarding.common.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

	@Column(nullable = false)
	private String name;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private QuestionType type;

	@Column(nullable = false)
	private boolean required;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servey_id")
	private Servey servey;

	@JsonManagedReference
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Option> options = new ArrayList<>();

	@Builder
	public Question(String name, String description, QuestionType type, boolean required) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;

		if ((type == QuestionType.SINGLE_LIST || type == QuestionType.MULTI_LIST) &&
			(options == null || options.isEmpty())) {
			throw new IllegalArgumentException("단일 선택 리스트 또는 다중 선택 리스트는 선택할 수 있는 후보가 포함되어야 합니다.");
		}
	}

	public void setServey(Servey servey) {
		this.servey = servey;
	}

	public void addOption(Option option) {
		this.options.add(option);
		option.setQuestion(this);
	}
}
