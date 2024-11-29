package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESEARCHITEMCHOICE")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "content"})
@EntityListeners(AuditingEntityListener.class)
public class ResearchItemChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable=false, updatable=false)
    private Long researchItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "researchItemId")
    private ResearchItem researchItem;

    @Column
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ResearchItemChoice create(String content, ResearchItem researchItem) {
//    public static ResearchItemChoice create(String content) {
        ResearchItemChoice enitity = new ResearchItemChoice();
        enitity.setContent(content);
        enitity.setResearchItem(researchItem);
        return enitity;
    }
}
