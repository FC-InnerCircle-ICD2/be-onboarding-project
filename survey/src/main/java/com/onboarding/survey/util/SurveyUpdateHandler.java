package com.onboarding.survey.util;


import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.QuestionStatus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SurveyUpdateHandler {

  public List<QuestionStatus> handleUpdate(List<Question> beforeQuestions, List<Question> afterQuestions) {
    List<QuestionStatus> statuses = new ArrayList<>();

    // 요청 데이터로부터 삭제 처리 확인
    for (Question after : afterQuestions) {
      if (after.isDeleted()) {
        Question existing = beforeQuestions.stream()
            .filter(q -> q.getTitle().equalsIgnoreCase(after.getTitle()))
            .findFirst()
            .orElse(null);
        if (existing != null) {
          statuses.add(QuestionStatus.deleted(existing));
        }
      }
    }

    // 기존 질문 상태 확인
    for (Question before : beforeQuestions) {
      Question matched = afterQuestions.stream()
          .filter(after -> after.getTitle().equalsIgnoreCase(before.getTitle()))
          .findFirst()
          .orElse(null);

      if (matched == null) {
        // 요청에 없는 기존 질문은 상태 유지
        statuses.add(QuestionStatus.unchanged(before));
      } else if (isQuestionUpdated(before, matched)) {
        // 속성 변경 확인
        statuses.add(QuestionStatus.updated(before, matched));
      }
    }

    // 신규 질문 확인
    for (Question after : afterQuestions) {
      boolean isNew = beforeQuestions.stream()
          .noneMatch(before -> before.getTitle().equalsIgnoreCase(after.getTitle()));
      if (isNew && !after.isDeleted()) {
        statuses.add(QuestionStatus.added(after));
      }
    }

    return statuses;
  }

  private boolean isQuestionUpdated(Question before, Question after) {
    return !before.getType().equals(after.getType()) ||
        before.isRequired() != after.isRequired() ||
        !before.getChoices().equals(after.getChoices()) ||
        !before.getDescription().equals(after.getDescription());
  }

}
