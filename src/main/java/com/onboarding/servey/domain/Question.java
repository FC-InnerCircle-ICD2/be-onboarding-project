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
import com.onboarding.servey.dto.request.QuestionRequest;

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

	private String answer;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servey_id")
	private Servey servey;

	@JsonManagedReference
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Option> options = new ArrayList<>();

	@Builder
	public Question(String name, String description, QuestionType type, boolean required, String answer) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
		this.answer = answer;
	}

	public void setServey(Servey servey) {
		this.servey = servey;
	}

	public void addOption(Option option) {
		this.options.add(option);
		option.setQuestion(this);
	}

	public static Question of(QuestionRequest questionRequest) {
		return Question.builder()
			.name(questionRequest.getName())
			.description(questionRequest.getDescription())
			.type(QuestionType.of(questionRequest.getType()))
			.required(questionRequest.isRequired())
			.build();
	}

	public QuestionEditor.QuestionEditorBuilder toEditor() {
		return QuestionEditor.builder()
			.name(name)
			.description(description)
			.type(type)
			.required(required)
			.answer(answer);
	}

	public void edit(QuestionEditor questionEditor) {
		name = questionEditor.getName();
		description = questionEditor.getDescription();
		type = questionEditor.getType();
		required = questionEditor.isRequired();
		answer = questionEditor.getAnswer();
	}
}
