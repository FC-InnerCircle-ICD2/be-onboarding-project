package com.onboarding.servey.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.onboarding.common.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {

	private Long serveyId;

	@Embedded
	private QuestionSnapShot questionSnapShot;

	@ManyToOne(cascade = CascadeType.ALL)
	private AnswerContent content;

	@Builder
	public Answer(Long serveyId, QuestionSnapShot questionSnapShot, AnswerContent content) {
		this.serveyId = serveyId;
		this.questionSnapShot = questionSnapShot;
		this.content = content;
	}

	public void validate() {
		content.validate(questionSnapShot.isRequired());
	}
}
