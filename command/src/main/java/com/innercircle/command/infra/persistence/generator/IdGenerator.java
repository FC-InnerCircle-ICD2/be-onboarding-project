package com.innercircle.command.infra.persistence.generator;

import java.util.UUID;

public class IdGenerator {

	private IdGenerator() {
		throw new UnsupportedOperationException();
	}

	public static UUID generate() {
		return UUID.randomUUID();
	}
}
