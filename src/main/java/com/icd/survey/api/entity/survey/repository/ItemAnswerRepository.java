package com.icd.survey.api.entity.survey.repository;

import com.icd.survey.api.entity.survey.ItemAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemAnswerRepository extends JpaRepository<ItemAnswer, Long> {
    Optional<List<ItemAnswer>> findAllByItemSeq(Long itemSeq);

}
