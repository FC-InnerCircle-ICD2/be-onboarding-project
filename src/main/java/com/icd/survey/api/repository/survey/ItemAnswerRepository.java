package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemAnswerRepository extends JpaRepository<ItemAnswer, Long> {

}
