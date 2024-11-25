package com.icd.survey.api.repository;

import com.icd.survey.api.entity.ItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemResponseRepository extends JpaRepository<ItemResponse, Long> {
}
