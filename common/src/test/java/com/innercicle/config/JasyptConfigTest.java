package com.innercicle.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JasyptConfigTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    @Test
    @DisplayName("암복호화 테스트")
    void encrypt () {
        // given
        String plainText = "seunggu";
        // when
        String encrypt = jasyptStringEncryptor.encrypt(plainText);
        // then
        assertEquals(plainText, jasyptStringEncryptor.decrypt(encrypt));

    }

}