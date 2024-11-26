package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESEARCHANSWER")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class ResearchAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long researchId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String response;
}
