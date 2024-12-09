package com.innercircle.surveryproject.modules.global;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * api Response 공통 유틸
 */
public class ResponseUtils {

    /**
     * 조회가 정상적으로 이뤄줬을때 반환하는 메소드
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    // Utility methods for creating ResponseEntity
    public static <T> ResponseEntity<?> ok(T data, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * 요청이 정상적으로 등록됐을때 반환하는 메소드
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<?> created(T data, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * 요청에서 에러가 발생했을때 반환하는 메소드
     *
     * @param message
     * @param status
     * @return
     */
    public static ResponseEntity<?> error(String message, HttpStatus status) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(status.value(), message, null);
        return ResponseEntity.status(status).body(apiResponse);
    }

    public static <T> ResponseEntity<?> error(String message, T data) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @Data
    public static class ApiResponse<T> {

        private int status;

        private String message;

        private T data;

        public ApiResponse(int status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

    }

}
