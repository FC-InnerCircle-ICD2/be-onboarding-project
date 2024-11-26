package ziwookim.be_onboarding_project.research.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ziwookim.be_onboarding_project.research.repository.ResearchRepository;
import ziwookim.be_onboarding_project.research.service.ResearchService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final ResearchRepository researchRepository;
}
