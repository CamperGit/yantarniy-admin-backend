package ru.ds.yantarniy.admin.backend.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Order(1)
public class HttpLoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        if (!log.isDebugEnabled()) {
            return;
        }
        StringBuilder logBuilding = new StringBuilder();
        logBuilding.append("===========================request begin================================================\n");
        logBuilding.append("URI         : ").append(request.getURI()).append("\n");
        logBuilding.append("Method      : ").append(request.getMethod()).append("\n");
        logBuilding.append("Headers     : ").append(request.getHeaders()).append("\n");
        logBuilding.append("Request body: ").append(new String(body, StandardCharsets.UTF_8)).append("\n");
        logBuilding.append("==========================request end================================================\n");
        log.debug(logBuilding.toString());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (!log.isDebugEnabled()) {
            return;
        }
        StringBuilder logBuilding = new StringBuilder();
        logBuilding.append("============================response begin==========================================\n");
        logBuilding.append("Status code  : ").append(response.getStatusCode()).append("\n");
        logBuilding.append("Status text  : ").append(response.getStatusText()).append("\n");
        logBuilding.append("Headers      : ").append(response.getHeaders()).append("\n");
        logBuilding.append("Response body: ").append(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8)).append("\n");
        logBuilding.append("============================response end=============================================\n");
        log.debug(logBuilding.toString());
    }
}
