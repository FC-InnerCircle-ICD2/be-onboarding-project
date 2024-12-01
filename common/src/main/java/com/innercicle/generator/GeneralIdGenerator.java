package com.innercicle.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.security.SecureRandom;

public class GeneralIdGenerator implements IdentifierGenerator {

    // starts at the year 2010
    private static final long EPOCH_MILLIS = 1262304000000L;
    // left shift amounts
    private static final int TIMESTAMP_SHIFT = 23;
    // exclusive
    private static final int MAX_RANDOM = 0x800000;

    private GeneralIdGenerator() {
    }

    @Override public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        long time = System.currentTimeMillis() - EPOCH_MILLIS;
        return (time << TIMESTAMP_SHIFT) + new SecureRandom().nextInt(MAX_RANDOM);
    }

}
