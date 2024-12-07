package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
