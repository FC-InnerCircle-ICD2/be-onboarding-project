package net.gentledot.survey.model.entity;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SurveyItemTest {

    @Test
    void createSurveyItem() {
        SurveyItem surveyItem = SurveyItem.of(
                "itemName",
                "itemDescription",
                SurveyItemType.TEXT,
                ItemRequired.REQUIRED
        );
        log.info("surveyItem = {}", surveyItem);;
        Assertions.assertThat(surveyItem).isNotNull();
        Assertions.assertThat(surveyItem.getAnswer()).isNull();

    }
}