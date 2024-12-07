package ziwookim.be_onboarding_project.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ziwookim.be_onboarding_project.common.response.CommonResponse;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;
import ziwookim.be_onboarding_project.common.web.error.HttpError;
import ziwookim.be_onboarding_project.common.web.exception.BadRequestException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class ApiConfiguration {

    private final ObjectMapper objectMapper;

    public ApiConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private List<Map<String, HttpError>> httpErrorMapList = createHttpErrorMapList();

    private List<Map<String, HttpError>> createHttpErrorMapList() {
        List<Map<String, HttpError>> httpErrorMapList = new ArrayList<>();

        httpErrorMapList.add(toHttpErrorMap(HttpErrors.values()));

        return httpErrorMapList;
    }

    private Map<String, HttpError> toHttpErrorMap(Enum<? extends HttpError>[] httpErrors) {
        return Arrays.stream(httpErrors)
                .map(httpError -> (HttpError)httpError)
                .collect(Collectors.toMap(
                        HttpError::getCode,
                        Function.identity(),
                        (a, b) -> b));
    }

    private HttpError valuesOf(String code, HttpError defaultHttpError) {
        return httpErrorMapList.stream()
                .map(httpErrorMap -> httpErrorMap.getOrDefault(code, null))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(defaultHttpError);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    private class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String responseBody = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
            log.info("fail to request, status : {}, response body : {}", response.getStatusCode(), responseBody);

            switch (response.getStatusCode()) {
//                case UNAUTHORIZED:
//                    throw UnauthorizedException.of();
//                case FORBIDDEN:
//                    throw ForbiddenException.of();
//                case NOT_FOUND:
//                    throw NotFoundException.of();
                default:
                    throw toBadRequestException(responseBody);
            }
        }

        private BadRequestException toBadRequestException(String responseBody) {
            if (StringUtils.isEmpty(responseBody)) {
                log.info("toBadRequestException, response body is empty");
            }

            CommonResponse commonResponse = null;
            try {
                commonResponse = objectMapper.readValue(responseBody, CommonResponse.class);
            } catch (Exception e) {
                log.error("fail to parse json : {}, ", responseBody, e);
            }

            if (commonResponse == null) {
                return BadRequestException.of(HttpErrors.BAD_REQUEST);
            }

            HttpError httpError = valuesOf(commonResponse.getCode(), HttpErrors.BAD_REQUEST);
            return BadRequestException.of(httpError);
        }

        private HttpError valuesOf(String code, HttpError defaultHttpError) {
            // Implement your logic for mapping the code to HttpError
            return HttpErrors.BAD_REQUEST; // Placeholder for demo purposes
        }
    }

}
