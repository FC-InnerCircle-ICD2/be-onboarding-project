package ic2.onboarding.survey.entity;

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
