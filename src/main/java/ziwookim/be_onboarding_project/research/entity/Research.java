package ziwookim.be_onboarding_project.research.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "research")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
public class Research {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
