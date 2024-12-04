package com.metsakurr.beonboardingproject.domain.survey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metsakurr.beonboardingproject.common.entity.BaseEntity;
import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.common.exception.ServiceException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.*;

@Entity(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @JsonIgnore
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "survey_idx", referencedColumnName = "idx")
    private Survey survey;

    @Comment("항목 이름")
    @Column(nullable = false)
    private String name;

    @Comment("항목 설명")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Comment("항목 입력 형태")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Comment("항목 필수 여부")
    @ColumnDefault("false")
    @Column(name = "required_yn", nullable = false)
    private boolean isRequired;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

    public void addOptions(Option option) {
        this.options.add(option);
        option.setQuestion(this);
    }

    @Builder
    private Question(
            Survey survey,
            String name,
            String description,
            QuestionType questionType,
            boolean isRequired,
            List<Option> options
    ) {
        this.survey = survey;
        this.name = name;
        this.description = description;
        this.questionType = questionType;
        this.isRequired = isRequired;
        this.options = options;
    }

    public void validAnswer(String answer) {
        // code review: ? Question 객체가 질문에 대한 정보를 가지고 있어 Answer 검증 코드를 Question에 작성했지만
        //  Question이 가지고 있어도 되는게 맞을지 모르겠음
        if (!isRequired && (answer == null || answer.isBlank())) {
            throw new ServiceException(ResponseCode.NOT_FOUND_REQUIRED_ANSWER);
        }

        switch (questionType) {
            case SHORT_SENTENCE:
                if (answer.length() > 255) {
                    throw new ServiceException(ResponseCode.INVALID_SHORT_SENTENCE_ANSWER);
                }
                break;
            case SINGLE_CHOICE:
            case MULTI_CHOICE:
                try {
                    List<Long> selectedOption = Arrays.stream(answer.split(","))
                            .map(String::trim)
                            .map(Long::parseLong)
                            .toList();
                    List<Long> optionIdxs = options.stream().map(Option::getIdx).toList();

                    boolean isContains = optionIdxs.stream()
                            .anyMatch(selectedOption::contains);
                    if (!isContains) {
                        throw new ServiceException(ResponseCode.INVALID_OPTION_IDX);
                    }
                } catch (NumberFormatException ex) {
                    throw new ServiceException(ResponseCode.INVALID_OPTION_IDX);
                }
                break;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Question question = (Question) obj;

        if(!Objects.equals(name, question.getName())) return false;
        if(!Objects.equals(description, question.getDescription())) return false;
        if(questionType != question.getQuestionType()) return false;
        if(isRequired != question.isRequired()) return false;

        return new HashSet<>(options).equals(new HashSet<>(question.getOptions()));
    }
}

