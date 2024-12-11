package com.onboarding.servey.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.onboarding.common.domain.BaseEntity;
import com.onboarding.common.exception.BaseException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "servey")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Servey extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "servey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();

	@Builder
	public Servey(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void addQuestion(Question question) {
		this.questions.add(question);
		question.setServey(this);
	}

	public void edit(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void validate() {
		if (questions.isEmpty()) {
			throw new BaseException("설문 받을 항목은 1개 이상이어야 합니다.");
		}
	}
}
