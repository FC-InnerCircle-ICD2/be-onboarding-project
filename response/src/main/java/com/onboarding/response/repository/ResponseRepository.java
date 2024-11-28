package com.onboarding.response.repository;

import com.onboarding.response.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ResponseRepository extends JpaRepository<Response, Long> {

}
