package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.survey.dto.ItemAnswerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "item_answer")
@ToString
public class ItemAnswer {
    @Id
    @Column(name = "answer_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerSeq;
    @Column(name = "answer", nullable = true)
    private String answer;
    @Column(name = "is_optional_answer", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Boolean isOptionalAnswer;
    @Column(name = "option_seq", nullable = true)
    private Long optionSeq;
    @Column(name = "option_answer", nullable = true)
    private String optionAnswer;

    @Column(name = "item_seq", nullable = false)
    private Long itemSeq;

    public static ItemAnswer createItemResponseRequest(ItemAnswerDto dto) {
        ItemAnswer itemAnswer = new ItemAnswer();
        itemAnswer.itemSeq = dto.getItemSeq();
        if (Boolean.TRUE.equals(dto.getIsOptionalAnswer())) {
            itemAnswer.isOptionalAnswer = dto.getIsOptionalAnswer();
            itemAnswer.optionSeq = dto.getOptionSeq();
            itemAnswer.optionAnswer = dto.getOptionAnswer();
        } else {
            itemAnswer.answer = dto.getAnswer();
        }
        return itemAnswer;
    }

    public ItemAnswerDto of() {
        ItemAnswerDto result =
                ItemAnswerDto.builder()
                        .answerSeq(this.answerSeq)
                        .build();
        if (Boolean.TRUE.equals(isOptionalAnswer)) {
            result.setOptionSeq(this.optionSeq);
            result.setOptionAnswer(this.optionAnswer);
        } else {
            result.setAnswer(this.answer);
        }
        return result;
    }
}
