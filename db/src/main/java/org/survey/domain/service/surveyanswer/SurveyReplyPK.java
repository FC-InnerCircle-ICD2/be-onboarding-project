package org.survey.domain.service.surveyanswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SurveyReplyPK implements Serializable {

    private Long id;
    private Long surveyId;
    private Long itemId;
}
