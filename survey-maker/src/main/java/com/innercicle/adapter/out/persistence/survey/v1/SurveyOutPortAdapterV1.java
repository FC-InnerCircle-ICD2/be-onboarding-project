package com.innercicle.adapter.out.persistence.survey.v1;

import com.innercicle.adapter.out.persistence.survey.entity.SurveyEntity;
import com.innercicle.adapter.out.persistence.survey.entity.SurveyItemEntity;
import com.innercicle.adapter.out.persistence.survey.repository.SurveyItemRepository;
import com.innercicle.adapter.out.persistence.survey.repository.SurveyRepository;
import com.innercicle.advice.exceptions.NotExistsSurveyException;
import com.innercicle.annotations.PersistenceAdapter;
import com.innercicle.application.port.out.v1.ModifySurveyOutPortV1;
import com.innercicle.application.port.out.v1.RegisterSurveyOutPortV1;
import com.innercicle.domain.v1.Survey;
import com.innercicle.domain.v1.SurveyItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class SurveyOutPortAdapterV1 implements RegisterSurveyOutPortV1, ModifySurveyOutPortV1 {

    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;

    @Override
    public Survey registerSurvey(Survey survey) {
        SurveyEntity surveyEntity = SurveyEntity.from(survey);
        surveyRepository.save(surveyEntity);
        return surveyEntity.mapToDomain();
    }

    @Override
    @Transactional
    public Survey modifySurvey(Survey survey) {
        SurveyEntity surveyEntity = surveyRepository.findById(survey.id()).orElseThrow(NotExistsSurveyException::new);
        surveyEntity.update(survey);
        modifySurveyItems(surveyEntity, survey);
        return survey;
    }

    /**
     * <h2>설문 항목 수정</h2>
     * - 기존 설문 항목 삭제 및 추가, 수정
     *
     * @param surveyEntity 설문 엔티티
     * @param survey       설문 도메인
     */
    private void modifySurveyItems(SurveyEntity surveyEntity, Survey survey) {
        List<SurveyItem> items = survey.items();
        if (!CollectionUtils.isEmpty(items)) {
            Map<Long, SurveyItemEntity> updateItemMap = new HashMap<>();
            deleteItemsAndAddUpdateItem(items, updateItemMap);
            saveNewItems(surveyEntity, items);
            for (SurveyItem item : items) {
                SurveyItemEntity itemEntity = updateItemMap.get(item.id());
                if (itemEntity != null) {
                    itemEntity.update(item);
                }
            }
        }
    }

    /**
     * <h2>기존 설문 항목 삭제 및 추가</h2>
     * 요청 항목에 없는데 저장된 항목에 있다면 삭제되었다는 의미이므로 삭제<br>
     * 요청 항목에 있고, 저장된 항목에도 있다면 수정해야 하기 때문에 수정 map에 저장해 준다.<br>
     *
     * @param items         요청 항목
     * @param updateItemMap 수정할 항목 맵
     */
    private void deleteItemsAndAddUpdateItem(List<SurveyItem> items, Map<Long, SurveyItemEntity> updateItemMap) {
        Map<Long, SurveyItemEntity> itemEntityMap = getSavedItemMap(items);
        for (SurveyItem item : items) {
            updateItemMap.put(item.id(), itemEntityMap.get(item.id()));
            itemEntityMap.remove(item.id());
        }
        List<Long> deleteIds = itemEntityMap.keySet().stream().toList();
        surveyItemRepository.deleteAllById(deleteIds);
    }

    /**
     * <h2>저장된 설문 항목 맵</h2>
     * - 요청 항목에 대한 저장된 항목 맵을 반환
     *
     * @param items 요청 항목
     * @return 데이터베이스에 저장된 항목 맵
     */
    private Map<Long, SurveyItemEntity> getSavedItemMap(List<SurveyItem> items) {
        List<Long> itemIds = items.stream().map(SurveyItem::id).filter(Objects::nonNull).toList();
        List<SurveyItemEntity> surveyItemList = surveyItemRepository.findAllById(itemIds);
        return surveyItemList.stream().collect(Collectors.toMap(SurveyItemEntity::getId, s -> s));
    }

    /**
     * <h2>새로운 항목 저장</h2>
     * - 새로운 항목을 저장
     *
     * @param surveyEntity 설문 엔티티
     * @param items        새로운 항목
     */
    private void saveNewItems(SurveyEntity surveyEntity, List<SurveyItem> items) {
        items.stream().filter(i -> i.id() == null).forEach(i -> {
            SurveyItemEntity itemEntity = SurveyItemEntity.from(i, surveyEntity);
            surveyItemRepository.save(itemEntity);
            surveyEntity.getItems().add(itemEntity);
        });
    }

}
