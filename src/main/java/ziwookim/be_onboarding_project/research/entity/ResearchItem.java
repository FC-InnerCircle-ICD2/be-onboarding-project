package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.*;
import lombok.*;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;

import java.util.List;

@Entity
@Table(name = "RESEARCHITEM")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "description", "itemType"})
public class ResearchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Research research;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private ResearchItemType itemType;

    @OneToMany
    private List<ResearchItemChoice> itemChoiceList;

    @Column
    private Boolean isRequired;
}
