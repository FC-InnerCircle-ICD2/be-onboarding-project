package ziwookim.be_onboarding_project.validator;

import ch.qos.logback.core.util.StringUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;
import ziwookim.be_onboarding_project.common.web.exception.BadRequestException;
import ziwookim.be_onboarding_project.research.dto.request.AddResearchRequestVo;
import ziwookim.be_onboarding_project.research.dto.request.EditResearchRequestVo;
import ziwookim.be_onboarding_project.research.dto.request.ResearchItemRequestVo;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;

import java.util.List;

import static ziwookim.be_onboarding_project.research.properties.ResearchProperties.MAX_ADDED_RESEARCH_ITEM;

@Slf4j
@UtilityClass
public class ResearchValidator {

    public void validate(AddResearchRequestVo requestVo) {
        if(StringUtil.isNullOrEmpty(requestVo.getTitle())) {
            log.error("title is empty.");
            throw BadRequestException.of(HttpErrors.EMPTY_TITLE);
        }

        if(StringUtil.isNullOrEmpty(requestVo.getDescription())) {
            log.error("description is empty.");
            throw BadRequestException.of(HttpErrors.EMPTY_TITLE);
        }

        if(requestVo.getItemVoList().isEmpty() || requestVo.getItemVoList().size() > MAX_ADDED_RESEARCH_ITEM) {
            log.error("this data has contained not proper research question size.");
            throw BadRequestException.of(HttpErrors.INVALID_RESEARCH_ITEM_SIZE);
        }

        requestVo.getItemVoList().forEach(i -> {
            if(!ResearchItemType.isValidItemType(i.getItemType())) {
                log.error("this data has contained not proper research itemTypes.");
                throw BadRequestException.of(HttpErrors.INVALID_RESEARCH_ITEM_TYPE);
            }
        });
    }

    public void validate(EditResearchRequestVo requestVo) {
        if(StringUtil.isNullOrEmpty(requestVo.getTitle())) {
            log.error("title is empty.");
            throw BadRequestException.of(HttpErrors.EMPTY_TITLE);
        }

        if(StringUtil.isNullOrEmpty(requestVo.getDescription())) {
            log.error("description is empty.");
            throw BadRequestException.of(HttpErrors.EMPTY_DESCRIPTION);
        }

        if(requestVo.getItemVoList().isEmpty() || requestVo.getItemVoList().size() > MAX_ADDED_RESEARCH_ITEM) {
            log.error("this data has contained not proper research question size.");
            throw BadRequestException.of(HttpErrors.INVALID_RESEARCH_ITEM_SIZE);
        }

        requestVo.getItemVoList().forEach(i -> {
            if(!ResearchItemType.isValidItemType(i.getItemType())) {
                log.error("this data has contained not proper research itemTypes.");
                throw BadRequestException.of(HttpErrors.INVALID_RESEARCH_ITEM_TYPE);
            }
        });
    }

    public void validate(List<ResearchItemRequestVo> researchItemRequestVoList) {
        researchItemRequestVoList.forEach(i -> {
            if(i.getItemChoiceList().isEmpty()) {
                log.error("selection question is required choices.");
                throw BadRequestException.of(HttpErrors.MISMATCH_RESEARCH_ITEM_CHOICE);
            }
        });
    }
}
