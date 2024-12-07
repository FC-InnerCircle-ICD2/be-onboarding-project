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
