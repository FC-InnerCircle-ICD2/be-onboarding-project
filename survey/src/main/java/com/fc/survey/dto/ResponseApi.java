package com.fc.survey.dto;

import lombok.Getter;

@Getter
public class ResponseApi<T> {
  private final String code;
  private final String msg;
  private final T data;

  public ResponseApi(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

}
