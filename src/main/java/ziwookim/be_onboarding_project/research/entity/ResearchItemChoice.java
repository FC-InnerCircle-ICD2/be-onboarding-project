package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESEARCHITEMCHOICE")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "content"})
public class ResearchItemChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ResearchItem researchItem;

    @Column
    private String content;
}
