package ziwookim.be_onboarding_project.research.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ziwookim.be_onboarding_project.research.entity.Research;
import ziwookim.be_onboarding_project.research.entity.ResearchItem;

import java.util.List;

@Repository
public interface ResearchItemRepository extends JpaRepository<ResearchItem, Long> {

    List<ResearchItem> findResearchItemsByResearchId(Long researchId);

    void deleteResearchItemsByResearchId(Long researchId);
}
