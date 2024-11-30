package org.survey.db.selectlist;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.survey.db.BaseEntity;
import org.survey.db.BaseStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "select_list")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SelectListEntity extends BaseEntity {

    @Column(nullable = false)
    private Long surveyId;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime unregisteredAt;
}
