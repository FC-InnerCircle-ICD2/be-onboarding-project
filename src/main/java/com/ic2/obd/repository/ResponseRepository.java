package com.ic2.obd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ic2.obd.domain.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {

}
