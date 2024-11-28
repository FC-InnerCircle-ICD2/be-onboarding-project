package ic2.onboarding.survey.entity;

import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "survey", cascade = CascadeType.PERSIST)
    private List<SurveySubmissionItem> submittedItems = new ArrayList<>();


    public Survey(String name, String description) {

        this.name = name;
        this.description = description;
    }


    public void update(String name,
                       String description,
                       List<SurveyItem> surveyItemsToBeReplaced) {

        this.name = name;
        this.description = description;

        // 교체할 항목에 기존 항목이 없다면 기존 항목 DELETE
        this.removeItemIfNotContained(surveyItemsToBeReplaced);

        // 신규 항목 추가 또는 수정
        surveyItemsToBeReplaced.forEach(item -> Optional.ofNullable(item.getId())
                .ifPresentOrElse(
                        // 변경 할 ID 존재 -> UPDATE
                        targetId -> this.updateItemInfo(targetId, item),

                        // 없다면 INSERT
                        () -> this.addItem(item)
                )
        );
    }


    public void addItem(SurveyItem newItem) {

        this.items.add(newItem);
        newItem.setSurvey(this);
    }


    public void addAllItems(List<SurveyItem> newItems) {

        if (this.items.addAll(newItems)) {
            newItems.forEach(item -> item.setSurvey(this));
        }
    }


    public void submitForm(List<SurveySubmissionItem> submissionItems) {

        // 제출한 단일선택 항목들
        List<Long> choices = submissionItems.stream()
                .filter(submissionItem -> submissionItem.getSurveyItem().isSingleChoice())
                .map(submissionItem -> submissionItem.getSurveyItem().getId())
                .toList();

        submissionItems.forEach(submissionItem -> {
            SurveyItem surveyItem = submissionItem.getSurveyItem();

            // 항목 답변 길이 체크
            String answer = submissionItem.getAnswer();
            if (!surveyItem.validAnswerLength(answer)) {
                throw new BizException(ErrorCode.NOT_VALIDATED);
            }

            /* 선택형 항목 추가 검사 */
            if (!surveyItem.isChoiceType()) {
                return;
            }

            // 선택 가능한 옵션 중 하나에 포함되는 답변인가?
            if (!surveyItem.containsChoice(answer)) {
                throw new BizException(ErrorCode.NOT_VALIDATED);
            }

            if (surveyItem.isMultipleChoice()) {
                return;
            }

            // 단일선택 타입이 1개 초과인지 검사
            long count = choices.stream()
                    .filter(itemId -> surveyItem.getId().equals(itemId))
                    .count();

            if (count > 1) {
                throw new BizException(ErrorCode.NOT_VALIDATED);
            }
        });

        /* 필수값 중 제출되지 않은 것이 있는지 확인 */
        // (1). 필수 ITEM ID Set
        Set<Long> requiredItemIds = this.items.stream()
                .filter(SurveyItem::getRequired)
                .map(SurveyItem::getId)
                .collect(Collectors.toSet());

        // (2). 제출한 ITEM ID Set
        Set<Long> submissionIds = submissionItems.stream()
                .map(submitted -> submitted.getSurveyItem().getId())
                .collect(Collectors.toSet());

        requiredItemIds.removeAll(submissionIds);
        // (1) - (2) 이후 (1)이 비어있지 않다면 필수값 미제출 항목 존재
        if (!requiredItemIds.isEmpty()) {
            throw new BizException(ErrorCode.NOT_VALIDATED);
        }

        // 저장
        if (this.submittedItems.addAll(submissionItems)) {
            submissionItems.forEach(item -> item.setSurvey(this));
        }
    }


    private void removeItemIfNotContained(List<SurveyItem> surveyItemsToBeReplaced) {

        // (1). 기존 항목 ID 집합
        Set<Long> storedItemIds = this.items.stream()
                .map(SurveyItem::getId)
                .collect(Collectors.toSet());

        // (2). 교체 항목 ID 집합
        Set<Long> requestIds = surveyItemsToBeReplaced.stream()
                .map(SurveyItem::getId)
                .collect(Collectors.toSet());

        // (1) - (2) = 지워질 ID 집합
        storedItemIds.removeAll(requestIds);
        Set<Long> toBeDeletedItemIds = new HashSet<>(storedItemIds);

        // DELETE
        this.items.removeIf(storedItem -> toBeDeletedItemIds.contains(storedItem.getId()));
    }


    private void updateItemInfo(Long targetId, SurveyItem newItem) {

        this.items.stream()
                .filter(storedItem -> storedItem.getId().equals(targetId))
                .findAny()
                .ifPresent(storedItem -> storedItem.update(newItem));
    }
}
