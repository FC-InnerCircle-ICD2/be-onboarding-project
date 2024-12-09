package org.survey.domain.service.surveyitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemPK implements Serializable {

    private Long id;
    private Long surveyId;
}
