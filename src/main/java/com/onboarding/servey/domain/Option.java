package com.onboarding.servey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onboarding.common.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option extends BaseEntity {

	@Column(nullable = false)
	private String number;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@Builder
	public Option(String number, Question question) {
		this.number = number;
		this.question = question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
