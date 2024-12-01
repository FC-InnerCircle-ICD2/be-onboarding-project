package ic2.onboarding.survey.entity;

import ic2.onboarding.survey.global.BizConstants;
import ic2.onboarding.survey.global.ItemInputType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "SURVEY_ID",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Survey survey;

    @Column(nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ItemInputType inputType;

    @Column(nullable = false)
    private Boolean required;

    @Column(length = 500)
    private String choices;


    public boolean isSingleChoice() {

        return inputType == ItemInputType.SINGLE_CHOICE;
    }


    public boolean isMultipleChoice() {

        return inputType == ItemInputType.MULTIPLE_CHOICE;
    }


    public boolean isChoiceType() {

        return inputType.isChoiceType();
    }


    public List<String> getChoiceList() {

        if (choices == null || !inputType.isChoiceType()) {
            return List.of();
        }
        return Arrays.stream(choices.split("\\|")).toList();
    }


    public void update(SurveyItem surveyItem) {

        this.name = surveyItem.getName();
        this.description = surveyItem.getDescription();
        this.inputType = surveyItem.getInputType();
        this.required = surveyItem.getRequired();
        this.choices = surveyItem.getChoices();
    }


    public boolean containsChoice(String choice) {

        if (!this.inputType.isChoiceType()) {
            return false;
        }
        return this.getChoiceList().contains(choice);
    }


    public boolean validAnswerLength(String answer) {

        // 단일 or 다중 선택이라면
        if (this.inputType.isChoiceType()) {
            if (answer == null || answer.isEmpty()) {
                return false;
            }

            return answer.length() >= BizConstants.MIN_CHOICE_ITEM_ANSWER_LENGTH
                    && answer.length() <= BizConstants.MAX_CHOICE_ITEM_ANSWER_LENGTH;
        }

        // 단답형
        if (this.inputType == ItemInputType.SHORT_ANSWER) {

            return answer.length() >= BizConstants.MIN_SHORT_ANSWER_LENGTH
                    && answer.length() <= BizConstants.MAX_SHORT_ANSWER_LENGTH;
        }

        // 장문형
        if (this.inputType == ItemInputType.LONG_ANSWER) {

            return answer.length() >= BizConstants.MIN_LONG_ANSWER_LENGTH
                    && answer.length() <= BizConstants.MAX_LONG_ANSWER_LENGTH;
        }

        return false;
    }

}
