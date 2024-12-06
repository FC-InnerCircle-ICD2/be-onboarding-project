package com.onboarding.core.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GlobalResponse <T>{
  private int code;
  private String message;
  private T data;


  public static GlobalResponse<Void> success() {
    return new GlobalResponse<>(200, "Success", null);
  }

  public static GlobalResponse<Void> success(String message) {
    return new GlobalResponse<>(200, message, null);
  }

  public static <T> GlobalResponse<T> success(T data) {
    return new GlobalResponse<>(200, "Success", data);
  }

  public static <T> GlobalResponse<T> success(String message, T data) {
    return new GlobalResponse<>(200, message, data);
  }

  public static <T> GlobalResponse<T> success(int code, String message, T data) {
    return new GlobalResponse<>(code, message, data);
  }


  public static GlobalResponse<Void> error() {
    return new GlobalResponse<>(500, "Internal Server Error", null);
  }

  public static GlobalResponse<Void> error(int code, String message) {
    return new GlobalResponse<>(code, message, null);
  }

  public static <T> GlobalResponse<T> error(int code, String message, T data) {
    return new GlobalResponse<>(code, message, data);
  }


}
