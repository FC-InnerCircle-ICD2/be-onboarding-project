package com.innercicle.common.container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class RedisTestContainer {

    private static final String DOCKER_REDIS_IMAGE = "redis:6-alpine";

    @Container
    public static final GenericContainer<?> REDIS_CONTAINER = new GenericContainer<>(DOCKER_REDIS_IMAGE)
        .withExposedPorts(6379)
        .withReuse(true);

    // 동적 설정값 매핑
    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379));
    }

}
