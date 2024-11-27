package com.innercircle.command.domain;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Identifier implements Serializable {

	private String id;

	public Identifier(String id) {
		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("Id must not be empty");
		}
		this.id = id;
	}

	@Override
	public String toString() {
		return this.id;
	}
}
