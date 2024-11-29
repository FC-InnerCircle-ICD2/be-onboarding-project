package ziwookim.be_onboarding_project.research.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ziwookim.be_onboarding_project.research.entity.ResearchAnswer;

@Repository
public interface ResearchAnswerRepository extends JpaRepository<ResearchAnswer, Long> {
}
