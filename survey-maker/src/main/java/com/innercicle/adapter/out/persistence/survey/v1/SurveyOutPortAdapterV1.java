package com.innercicle.adapter.out.persistence.survey.v1;

import com.innercicle.adapter.out.persistence.survey.entity.SurveyEntity;
import com.innercicle.adapter.out.persistence.survey.entity.SurveyItemEntity;
import com.innercicle.adapter.out.persistence.survey.repository.SurveyItemRepository;
import com.innercicle.adapter.out.persistence.survey.repository.SurveyRepository;
import com.innercicle.annotations.PersistenceAdapter;
import com.innercicle.application.port.out.v1.ModifySurveyOutPortV1;
import com.innercicle.application.port.out.v1.RegisterSurveyOutPortV1;
import com.innercicle.domain.v1.Survey;
import com.innercicle.domain.v1.SurveyItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
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
        surveyRepository.findById(survey.id())
            .ifPresent(surveyEntity -> surveyEntity.update(survey));
        modifySurveyItemsIfExists(survey);
        return survey;
    }

    private void modifySurveyItemsIfExists(Survey survey) {
        List<SurveyItem> items = survey.items();
        if (!CollectionUtils.isEmpty(items)) {
            List<Long> itemIds = items.stream().map(SurveyItem::id).toList();
            List<SurveyItemEntity> surveyItemList = surveyItemRepository.findAllById(itemIds);
            Map<Long, SurveyItemEntity> itemEntityMap = surveyItemList.stream().collect(Collectors.toMap(SurveyItemEntity::getId, s -> s));
            for (SurveyItem item : items) {
                SurveyItemEntity itemEntity = itemEntityMap.get(item.id());
                if (itemEntity != null) {
                    itemEntity.update(item);
                }
            }
        }
    }

}
