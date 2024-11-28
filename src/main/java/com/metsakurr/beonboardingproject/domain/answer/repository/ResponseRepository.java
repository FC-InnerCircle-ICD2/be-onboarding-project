package com.metsakurr.beonboardingproject.domain.answer.repository;

import com.metsakurr.beonboardingproject.domain.answer.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
