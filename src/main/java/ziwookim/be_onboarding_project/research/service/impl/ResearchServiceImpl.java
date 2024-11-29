package ziwookim.be_onboarding_project.research.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;
import ziwookim.be_onboarding_project.common.web.exception.BadRequestException;
import ziwookim.be_onboarding_project.research.dto.request.*;
import ziwookim.be_onboarding_project.research.dto.request.ResearchAnswerVo;
import ziwookim.be_onboarding_project.research.dto.response.ResearchAnswerResponse;
import ziwookim.be_onboarding_project.research.entity.Research;
import ziwookim.be_onboarding_project.research.entity.ResearchAnswer;
import ziwookim.be_onboarding_project.research.entity.ResearchItem;
import ziwookim.be_onboarding_project.research.entity.ResearchItemChoice;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;
import ziwookim.be_onboarding_project.research.model.*;
import ziwookim.be_onboarding_project.research.repository.ResearchAnswerRepository;
import ziwookim.be_onboarding_project.research.repository.ResearchItemChoiceRepository;
import ziwookim.be_onboarding_project.research.repository.ResearchItemRepository;
import ziwookim.be_onboarding_project.research.repository.ResearchRepository;
import ziwookim.be_onboarding_project.research.service.ResearchService;
import ziwookim.be_onboarding_project.validator.ResearchValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final ResearchRepository researchRepository;
    private final ResearchItemRepository researchItemRepository;
    private final ResearchItemChoiceRepository researchItemChoiceRepository;
    private final ResearchAnswerRepository researchAnswerRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResearchVo addResearch(AddResearchRequestVo requestVo) {

        // research, researchItem validation check
        ResearchValidator.validate(requestVo);

        List<ResearchItemRequestVo> selectionResearchItemList = requestVo.getItemVoList().stream()
                .filter(i -> i.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || i.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType()))
                .toList();
        log.info("selectionResearchItemSize: {}", selectionResearchItemList.size());

        // researchItem, researchItemChoice validation check
        ResearchValidator.validate(selectionResearchItemList);

        // insert research
        Research research = Research.create(requestVo.getTitle(), requestVo.getDescription());
        research = researchRepository.save(research);

        List<ResearchItem> researchItemList = new ArrayList<>();
        List<ResearchItemChoice> researchItemChoiceList = new ArrayList<>();

        for(ResearchItemRequestVo researchItemRequestVo : requestVo.getItemVoList()) {
            ResearchItem researchItem = ResearchItem.create(researchItemRequestVo.getName(), researchItemRequestVo.getDescription(), researchItemRequestVo.getItemType(), researchItemRequestVo.getIsRequired(), research);
            // insert researchItem
            researchItem = researchItemRepository.save(researchItem);
            log.info("saved researchItem id: {}", researchItem.getId());

            researchItemChoiceList = new ArrayList<>();

            // insert researchItemChoice if researchItemType is a kind of selection type.
            if (researchItem.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || researchItem.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType())) {
                for (ResearchItemChoiceRequestVo itemChoiceVo : researchItemRequestVo.getItemChoiceList()) {
                    log.info("itemId: {}, itemChoiceListSize: {}", researchItem.getId(), researchItemRequestVo.getItemChoiceList().size());

                    ResearchItemChoice itemChoice = ResearchItemChoice.create(itemChoiceVo.getContent(), researchItem);
                    // insert researchItemChoice
                    itemChoice = researchItemChoiceRepository.save(itemChoice);
                    researchItemChoiceList.add(itemChoice);
                }
                researchItem.setItemChoiceList(researchItemChoiceList);
            }
            researchItemList.add(researchItem);
        }
        research.setResearchItems(researchItemList);

        return convertResearchVo(research);
    }

    @Override
    @Transactional
    public ResearchVo editResearch(EditResearchRequestVo requestVo) {
        // validate researchId
        Research research = researchRepository.findById(requestVo.getResearchId())
            .orElseThrow(() -> {
               log.error("this researchId is not found.");
                return BadRequestException.of(HttpErrors.RESEARCH_NOT_FOUND);
            });
        log.info("original research title: {}, description: {}", research.getTitle(), research.getDescription());

        // research, researchItem validation check
        ResearchValidator.validate(requestVo);

        List<ResearchItemRequestVo> selectionResearchItemList = requestVo.getItemVoList().stream()
                .filter(i -> i.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || i.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType()))
                .toList();

        // researchItem, researchItemChoice validation check
        ResearchValidator.validate(selectionResearchItemList);

        // get original researchItemList
        List<ResearchItem> researchItemList = researchItemRepository.findResearchItemsByResearchId(requestVo.getResearchId());

        // delete original researchItemChoiceList
        for(ResearchItem item : researchItemList) {
            log.info("original itemId: {}, itemType: {}", item.getId(), item.getItemType());
            if(item.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || item.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType())) {
                researchItemChoiceRepository.deleteResearchItemChoicesByResearchItemId(item.getId());
            }
        }

        // delete original researchItemList
        researchItemRepository.deleteResearchItemsByResearchId(requestVo.getResearchId());

        researchItemList = new ArrayList<>();
        List<ResearchItemChoice> researchItemChoiceList = new ArrayList<>();

        for(ResearchItemRequestVo researchItemRequestVo : requestVo.getItemVoList()) {
            ResearchItem researchItem = ResearchItem.create(researchItemRequestVo.getName(), researchItemRequestVo.getDescription(), researchItemRequestVo.getItemType(), researchItemRequestVo.getIsRequired(), research);
            // insert researchItem
            researchItem = researchItemRepository.save(researchItem);
            log.info("saved researchItem id: {}", researchItem.getId());

            researchItemChoiceList = new ArrayList<>();

            // insert researchItemChoice if researchItemType is a kind of selection type.
            if (researchItem.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || researchItem.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType())) {
                for (ResearchItemChoiceRequestVo itemChoiceVo : researchItemRequestVo.getItemChoiceList()) {

                    log.info("itemId: {}, itemChoiceListSize: {}", researchItem.getId(), researchItemRequestVo.getItemChoiceList().size());

                    ResearchItemChoice itemChoice = ResearchItemChoice.create(itemChoiceVo.getContent(), researchItem);
                    // insert researchItemChoice
                    itemChoice = researchItemChoiceRepository.save(itemChoice);
                    researchItemChoiceList.add(itemChoice);
                }
                researchItem.setItemChoiceList(researchItemChoiceList);
            }
            researchItemList.add(researchItem);
        }
        research.setResearchItems(researchItemList);

        research.setTitle(requestVo.getTitle());
        research.setDescription(requestVo.getDescription());

        // update research
        research = researchRepository.save(research);

        return convertResearchVo(research);
    }

    @Override
    @Transactional
    public ResearchAnswerResearchVo submitResearchAnswer(SubmitResearchRequestVo requestVo) throws JsonProcessingException {
        // validate researchId
        Research research = researchRepository.findById(requestVo.getResearchId())
                .orElseThrow(() -> {
                    log.error("this researchId is not found.");
                    return BadRequestException.of(HttpErrors.RESEARCH_NOT_FOUND);
                });

        List<ResearchAnswerVo> answerVoList = requestVo.getAnswerVoList();

        if(answerVoList.size() != research.getResearchItems().size()) {
            log.error("mismatched items and answers");
            throw BadRequestException.of(HttpErrors.MISMATCH_RESEARCH_ITEM_ANSWER_SIZE);
        }

        for(int i=0; i < research.getResearchItems().size(); i++) {
            ResearchItem researchItem = research.getResearchItems().get(i);
            ResearchAnswerVo answerVo = answerVoList.get(i);

            if(!answerVo.isValidAnswerType()) {
                log.error("invalid research answer data.");
                throw BadRequestException.of(HttpErrors.INVALID_RESEARCH_ANSWER);
            }

            if(researchItem.getIsRequired() && answerVo.isEmptyStringAnswerType()) {
                log.error("required item is ignored.");
                throw BadRequestException.of(HttpErrors.IGNORED_REQUIRED_ITEM);
            }

            if(!isValidResearchItemTypeAndAnswer(researchItem, answerVo)) {
                log.error("this itemType and answer data does not matched.");
                throw BadRequestException.of(HttpErrors.MISMATCH_RESEARCH_ITEM_ANSWER);
            }
        }

        ResearchAnswerResearchVo researchAnswerVo = convertResearchAnswerVo(research, answerVoList);
        String data = objectMapper.writeValueAsString(researchAnswerVo);

        researchAnswerRepository.save(ResearchAnswer.create(requestVo.getResearchId(), data));

        return researchAnswerVo;
    }

    @Override
    @Transactional(readOnly = true)
    public ResearchAnswerResponse getResearchAnswer(Long researchAnswerId) {
        ResearchAnswer researchAnswer = researchAnswerRepository.findById(researchAnswerId)
                .orElseThrow(() -> {
                    log.error("this researchAnswerId is not found.");
                    return BadRequestException.of(HttpErrors.RESEARCH_ANSWER_NOT_FOUND);
                });

        return null;
    }

    public ResearchVo convertResearchVo(Research research) {
        List<ResearchItemVo> researchItemVoList = new ArrayList<>();
        List<ResearchItemChoiceVo> researchItemChoiceVoList;

        for(ResearchItem item : research.getResearchItems()) {
            researchItemChoiceVoList = new ArrayList<>();

            if(item.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || item.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType())) {
                for(ResearchItemChoice choice : item.getItemChoiceList()) {
                    researchItemChoiceVoList.add(ResearchItemChoiceVo.of(choice.getId(), choice.getContent()));
                }
            }
            log.info("researchItemId: {}, researchItemTypeName: {}", item.getId(), ResearchItemType.getResearchItemTypeName(item.getItemType()));
            researchItemVoList.add(ResearchItemVo.of(item.getId(), item.getName(), item.getDescription(), item.getItemType(), ResearchItemType.getResearchItemTypeName(item.getItemType()), item.getIsRequired(), researchItemChoiceVoList));
        }

        return ResearchVo.of(research.getId(), research.getTitle(), research.getDescription(), researchItemVoList);
    }

    public boolean isValidResearchItemTypeAndAnswer(ResearchItem researchItem, ResearchAnswerVo answerVo) {
        switch (ResearchItemType.getResearchItemTypeName(researchItem.getItemType())) {
            case "SHORT_ANSWER", "LONG_SENTENCE":
                if(answerVo.getAnswer() instanceof String) {
                    return true;
                }
            case "SINGLE_SELECTION":
                if(answerVo.getAnswer() instanceof Long) {
                    return true;
                }
            case "MULTIPLE_SELECTION":
                if(answerVo.getAnswer() instanceof Long[]) {
                    return true;
                }
            default:
                return false;
        }
    }

    public ResearchAnswerResearchVo convertResearchAnswerVo(Research research, List<ResearchAnswerVo> answerVoList) {
        List<ResearchAnswerResearchItemVo> researchItemVoList = new ArrayList<>();
        List<ResearchItemChoiceVo> researchItemChoiceVoList;

        for(int i=0; i<research.getResearchItems().size(); i++) {
            researchItemChoiceVoList = new ArrayList<>();
            ResearchItem item = research.getResearchItems().get(i);

            if(item.getItemType().equals(ResearchItemType.SINGLE_SELECTION.getItemType()) || item.getItemType().equals(ResearchItemType.MULTIPLE_SELECTION.getItemType())) {
                for(ResearchItemChoice choice : item.getItemChoiceList()) {
                    researchItemChoiceVoList.add(ResearchItemChoiceVo.of(choice.getId(), choice.getContent()));
                }
            }
            log.info("researchItemId: {}, researchItemTypeName: {}", item.getId(), ResearchItemType.getResearchItemTypeName(item.getItemType()));
            researchItemVoList.add(ResearchAnswerResearchItemVo.of(item.getId(), item.getName(), item.getDescription(), item.getItemType(), ResearchItemType.getResearchItemTypeName(item.getItemType()), item.getIsRequired(), researchItemChoiceVoList, answerVoList.get(i)));
        }

        return ResearchAnswerResearchVo.of(research.getId(), research.getTitle(), research.getDescription(), researchItemVoList);
    }
}
