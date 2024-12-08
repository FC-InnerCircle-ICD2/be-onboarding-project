package com.innercircle.surveryproject.global.utils;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 파일 유틸 클래스
 * 테스트케이스 json데이터를 읽기 위한 유틸
 */
public class FileUtils {

    /**
     * 파일 내용을 String으로 반환합니다.
     *
     * @param fileName 읽을 파일의 이름
     * @return 파일 내용이 담긴 String
     * @throws IOException 파일을 읽을 수 없는 경우 예외 발생
     */
    public static String readFileAsString(String fileName) throws IOException {
        try (InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("파일이 클래스패스에서 발견되지 않았습니다: " + fileName);
            }

            // InputStream을 문자열로 변환
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("파일 읽기 중 오류 발생: " + fileName, e);
        }
    }

    /**
     * 파일을 읽고 JSON 데이터로 변환합니다.
     *
     * @param fileName 읽을 파일의 이름
     * @return JsonNode 객체
     * @throws IOException 파일을 읽거나 JSON 파싱 실패 시 예외 발생
     */
    public static JsonNode readFileAsJson(String fileName) throws IOException {
        try (InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            ObjectMapper objectMapper = new ObjectMapper();
            if (inputStream == null) {
                throw new Exception("파일이 클래스패스에서 발견되지 않았습니다: " + fileName);
            }
            return objectMapper.readTree(inputStream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
