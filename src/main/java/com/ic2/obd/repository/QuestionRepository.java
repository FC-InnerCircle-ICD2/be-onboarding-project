package com.ic2.obd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ic2.obd.domain.Question;

/**
 * 질문(Question) 엔티티와 데이터베이스 간의 인터페이스.
 * JpaRepository를 상속받아 기본 CRUD 기능을 제공한다.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {}
