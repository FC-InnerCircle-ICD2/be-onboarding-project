package com.innercicle.application.service.common.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innercicle.common.annotations.MockMvcTest;
import com.innercicle.common.container.RedisTestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcTest
public class AbstractMvcTestContainer extends RedisTestContainer {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
