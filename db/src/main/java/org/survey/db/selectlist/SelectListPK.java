package org.survey.db.selectlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectListPK implements Serializable {

    private Long id;
    private Long surveyId;
    private Long itemId;
}
