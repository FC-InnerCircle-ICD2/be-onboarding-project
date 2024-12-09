package org.innercircle.surveyapiapplication.domain.surveyItem.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class SurveyItemId implements Serializable {

    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private int version;

}
