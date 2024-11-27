package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItemResponseRepository extends JpaRepository<ItemResponse, Long> {
}
