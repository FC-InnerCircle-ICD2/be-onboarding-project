package com.onboarding.servey.domain;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.onboarding.common.domain.BaseEntity;
import com.onboarding.common.exception.BaseException;
import com.onboarding.servey.model.QuestionType;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servey_id")
	private Servey servey;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Option> options = new ArrayList<>();

	@Builder
	public Question(String name, String description, QuestionType type, boolean required) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
	}

	public void setServey(Servey servey) {
		this.servey = servey;
	}

	public void addOption(Option option) {
		this.options.add(option);
		option.setQuestion(this);
	}

	public void edit(String name, String description, QuestionType type, boolean required) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
	}

	public void validate() {
		if (Arrays.asList(QuestionType.SHORT_TYPE, QuestionType.LONG_TYPE).contains(type) && !options.isEmpty()) {
			throw new BaseException("SHORT_TYPE(단문형) 또는 LONG_TYPE(장문형)인 경우 선택할 수 있는 후보를 포함할 수 없습니다.");
		} else if (Arrays.asList(QuestionType.SINGLE_LIST, QuestionType.MULTI_LIST).contains(type) && options.isEmpty()) {
			throw new BaseException("SINGLE_LIST(단일 선택 리스트) 또는 MULTI_LIST(다중 선택 리스트)인 경우 선택할 수 있는 후보를 포함하여야 합니다.");
		}
	}

	public QuestionSnapShot getSnapShot() {
		return QuestionSnapShot.builder()
			.questionId(this.getId())
			.name(name)
			.description(description)
			.type(type)
			.required(required)
			.build();
	}
}
