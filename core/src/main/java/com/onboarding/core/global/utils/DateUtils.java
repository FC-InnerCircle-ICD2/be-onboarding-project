package com.onboarding.core.global.utils;

import java.time.format.DateTimeFormatter;

public class DateUtils {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static String formatDateTimeToString(java.time.LocalDateTime dateTime) {
    return dateTime.format(FORMATTER);
  }
}
