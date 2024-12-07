package com.survey.www.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@TestConfiguration
public class TestConfig implements WebMvcConfigurer {
    @PersistenceContext private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Java 8 Date/Time API 지원 모듈 등록
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 빈 객체(Null)에 대해 에러를 방지하기 위한 설정
        return objectMapper;
    }
}
