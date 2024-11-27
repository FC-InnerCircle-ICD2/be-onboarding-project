package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemResponseOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ResponseOptionRepository extends JpaRepository<ItemResponseOption, Long> {
    Optional<List<ItemResponseOption>> findByItemSeq(Long itemSeq);
}
