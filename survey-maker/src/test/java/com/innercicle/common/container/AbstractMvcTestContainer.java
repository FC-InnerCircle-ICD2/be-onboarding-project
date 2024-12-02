package com.innercicle.common.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innercicle.common.annotations.MockMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcTest
public class AbstractMvcTestContainer {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
