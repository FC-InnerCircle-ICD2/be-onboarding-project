package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemResponseOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseOptionRepository extends JpaRepository<ItemResponseOption, Long> {
}
