package ziwookim.be_onboarding_project.research.repository;

import ziwookim.be_onboarding_project.research.entity.ResearchAnswer;

import java.util.List;


public interface ResearchAnswerRepositoryCustom {

    List<ResearchAnswer> searchResearchAnswer(String keyword);
}
