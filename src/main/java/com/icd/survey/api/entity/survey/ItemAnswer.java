package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import com.icd.survey.api.entity.survey.embedable.LongAnswer;
import com.icd.survey.api.entity.survey.embedable.MultiChoice;
import com.icd.survey.api.entity.survey.embedable.ShortAnswer;
import com.icd.survey.api.entity.survey.embedable.SingleChoice;
import com.icd.survey.api.enums.survey.ResponseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "item_answer")
public class ItemAnswer {
    @Id
    @Column(name = "answer_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerSeq;
    @Column(name = "is_optional_answer", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Boolean isOptionalAnswer;
    /*@Column(name = "answer", nullable = true)
    private String answer;
    @Column(name = "option_seq", nullable = true)
    private Long optionSeq;*/
    @Column(name = "option_answer", nullable = true)
    private String optionAnswer;
    @Column(name = "response_type", nullable = false)
    private Integer responseType;

    @Embedded
    private ShortAnswer shortAnswer;
    @Embedded
    private LongAnswer longAnswer;
    @Embedded
    private SingleChoice singleChoice;
    @Embedded
    private MultiChoice multiChoice;

    @Column(name = "item_seq", nullable = false)
    private Long itemSeq;

    public static ItemAnswer createItemResponseRequest(ItemAnswerDto dto) {
        ItemAnswer itemAnswer = new ItemAnswer();
        Integer responseType = dto.getResponseType();
        itemAnswer.responseType = dto.getResponseType();
        itemAnswer.itemSeq = dto.getItemSeq();
        itemAnswer.isOptionalAnswer = dto.getIsOptionalAnswer();

        if (responseType.equals(ResponseType.SHORT_ANSWER.getType())) {
            itemAnswer.shortAnswer = new ShortAnswer(dto.getAnswer());
        } else if (responseType.equals(ResponseType.LONG_ANSWER.getType())) {
            itemAnswer.longAnswer = new LongAnswer(dto.getAnswer());
        } else if (responseType.equals(ResponseType.SINGLE_CHOICE.getType())) {
            itemAnswer.singleChoice = new SingleChoice(dto.getOptionSeq());
            itemAnswer.optionAnswer = dto.getOptionAnswer();
        } else if (responseType.equals(ResponseType.MULTI_CHOICE.getType())) {
            itemAnswer.multiChoice = new MultiChoice(dto.getOptionSeq());
            itemAnswer.optionAnswer = dto.getOptionAnswer();
        }

        /*if (Boolean.TRUE.equals(dto.getIsOptionalAnswer())) {
            itemAnswer.isOptionalAnswer = dto.getIsOptionalAnswer();
            itemAnswer.optionSeq = dto.getOptionSeq();
            itemAnswer.optionAnswer = dto.getOptionAnswer();
        } else {
            itemAnswer.answer = dto.getAnswer();
        }*/
        return itemAnswer;
    }

    public ItemAnswerDto of() {
        ItemAnswerDto result =
                ItemAnswerDto.builder()
                        .answerSeq(this.answerSeq)
                        .responseType(this.responseType)
                        .isOptionalAnswer(this.isOptionalAnswer)
                        .build();

        if (responseType.equals(ResponseType.SHORT_ANSWER.getType())) {
            result.setShortAnswer(this.shortAnswer.getShortAnswer());
        } else if (responseType.equals(ResponseType.LONG_ANSWER.getType())) {
            result.setLongAnswer(this.longAnswer.getLongAnswer());
        } else if (responseType.equals(ResponseType.SINGLE_CHOICE.getType())) {
            result.setSingleChoiceOptionSeq(this.singleChoice.getOptionSeq());
            result.setAnswer(this.optionAnswer);
        } else if (responseType.equals(ResponseType.MULTI_CHOICE.getType())) {
            result.setMultiChoiceOptionSeq(this.multiChoice.getMultiOptionSeq());
            result.setAnswer(this.optionAnswer);
        }

        /*if (Boolean.TRUE.equals(isOptionalAnswer)) {
            result.setOptionSeq(this.optionSeq);
            result.setOptionAnswer(this.optionAnswer);
        } else {
            result.setAnswer(this.answer);
        }*/
        return result;
    }
}
