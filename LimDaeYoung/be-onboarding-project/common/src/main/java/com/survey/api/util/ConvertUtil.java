package com.survey.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertUtil {
	public static int stringToInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String objectToJsonString(Object src) {
		try {
			return new ObjectMapper().writeValueAsString(src);
		} catch (Exception e) {
			return null;
		}
	}

	public static Long stringToLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return 0L;
		}
	}
}
