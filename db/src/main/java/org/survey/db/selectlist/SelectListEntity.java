package org.survey.db.selectlist;

import jakarta.persistence.*;
import lombok.*;
import org.survey.db.BaseStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "select_list")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SelectListPK.class)
public class SelectListEntity{

    @Id
    private Long id;

    @Id
    private Long surveyId;

    @Id
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
