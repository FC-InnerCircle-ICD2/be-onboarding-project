package com.ic2.obd.exception;

/**
 * 에러 응답 데이터를 정의하는 클래스.
 */
public class ErrorResponse {
	private String message;
    private int status;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    // Getter, Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
