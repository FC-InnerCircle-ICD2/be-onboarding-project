package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "RESEARCHITEM")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "description", "itemType"})
@EntityListeners(AuditingEntityListener.class)
public class ResearchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable=false, updatable=false)
    private Long researchId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "researchId")
    private Research research;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer itemType;

    @Column
    private Boolean isRequired;

    @OneToMany(mappedBy = "researchItem")
    private List<ResearchItemChoice> itemChoiceList;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ResearchItem create(String name, String description, Integer itemType, Boolean isRequired, Research research) {
//    public static ResearchItem create(String name, String description, Integer itemType, Boolean isRequired) {
        ResearchItem entity = new ResearchItem();

        entity.setName(name);
        entity.setDescription(description);
        entity.setItemType(itemType);
        entity.setIsRequired(isRequired);
        entity.setResearch(research);

        return entity;
    }
}
