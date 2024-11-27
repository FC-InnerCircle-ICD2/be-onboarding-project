package com.ic2.obd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ic2.obd.domain.Survey;

/**
 * 설문조사(Survey) 엔티티와 데이터베이스 간의 인터페이스.
 * JpaRepository를 상속받아 기본 CRUD 기능을 제공한다.
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {}
