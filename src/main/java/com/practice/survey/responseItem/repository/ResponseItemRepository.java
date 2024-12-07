package com.practice.survey.responseItem.repository;

import com.practice.survey.responseItem.model.entity.ResponseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseItemRepository extends JpaRepository<ResponseItem, Long> {
}
