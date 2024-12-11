package com.onboarding.servey.domain;

import javax.persistence.CascadeType;
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
	private Long questionId;

	@ManyToOne(cascade = CascadeType.ALL)
	private AnswerContent content;

	@Builder
	public Answer(Long serveyId, Long questionId, AnswerContent content) {
		this.serveyId = serveyId;
		this.questionId = questionId;
		this.content = content;
	}
}
