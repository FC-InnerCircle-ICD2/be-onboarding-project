package com.icd.survey.api.repository;

import com.icd.survey.api.entity.ItemResponseOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseOptionRepository extends JpaRepository<ItemResponseOption, Long> {
}
