package ziwookim.be_onboarding_project.research.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ziwookim.be_onboarding_project.research.entity.ResearchItemChoice;

import java.util.List;

@Repository
public interface ResearchItemChoiceRepository extends JpaRepository<ResearchItemChoice, Long> {

    void deleteResearchItemChoicesByResearchItemId(Long researchItemId);
}
