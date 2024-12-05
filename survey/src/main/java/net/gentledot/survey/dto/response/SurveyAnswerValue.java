package net.gentledot.survey.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.model.entity.surveyanswer.SurveyAnswerSubmission;
import net.gentledot.survey.model.entity.surveyanswer.SurveyQuestionAnswerSnapshot;
import net.gentledot.survey.model.entity.surveyanswer.SurveyQuestionSnapshot;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyAnswerValue {
    private final Long answerId;
    private final List<SurveyAnswerItem> answers;

    public static SurveyAnswerValue of(Long answerId, List<SurveyAnswerSubmission> submittedAnswers) {
        return SurveyAnswerValue.of(answerId, submittedAnswers, null, null);
    }

    public static SurveyAnswerValue of(Long answerId, List<SurveyAnswerSubmission> submittedAnswers, String questionName, String answerValue) {
        List<SurveyAnswerItem> answerItems = submittedAnswers.stream()
                .map(SurveyAnswerValue::generateSurveyAnswerItem)
                .filter(answer -> filterAnswer(answer, questionName, answerValue))
                .collect(Collectors.toList());
        return new SurveyAnswerValue(
                answerId,
                answerItems
        );
    }

    private static SurveyAnswerItem generateSurveyAnswerItem(SurveyAnswerSubmission answerSubmission) {
        SurveyQuestionSnapshot surveyQuestionSnapshot = answerSubmission.getSurveyQuestionSnapshot();
        SurveyQuestionAnswerSnapshot surveyQuestionAnswerSnapshot = answerSubmission.getSurveyQuestionAnswerSnapshot();
        String answer = surveyQuestionAnswerSnapshot.getAnswer(surveyQuestionSnapshot.getAnswerType());
        return new SurveyAnswerItem(surveyQuestionSnapshot.getItemName(), answer);
    }

    private static boolean filterAnswer(SurveyAnswerItem answer, String questionName, String answerValue) {
        // request에서 questionName과 answerValue가 없으면 통과
        if (StringUtils.isEmpty(questionName) && StringUtils.isEmpty(answerValue)) {
            return true;
        }

        boolean matchesQuestionName = StringUtils.isEmpty(questionName) ||
                                      answer.questionName().equalsIgnoreCase(questionName);
        boolean matchesAnswerValue = StringUtils.isEmpty(answerValue) ||
                                     answer.answerValue().contains(answerValue);

        return matchesQuestionName && matchesAnswerValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SurveyAnswerValue that = (SurveyAnswerValue) o;
        return Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(answerId);
    }
}
