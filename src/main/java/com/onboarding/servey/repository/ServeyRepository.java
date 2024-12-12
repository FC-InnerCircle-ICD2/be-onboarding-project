package com.onboarding.servey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onboarding.servey.domain.Servey;

public interface ServeyRepository extends JpaRepository<Servey, Long> {
}
