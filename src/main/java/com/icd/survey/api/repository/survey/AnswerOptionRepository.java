package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemAnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerOptionRepository extends JpaRepository<ItemAnswerOption, Long> {
    Optional<List<ItemAnswerOption>> findByItemSeq(Long itemSeq);
}
