package com.wanted.preonboarding.auth.filter;

import com.wanted.preonboarding.module.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class CustomLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        RequestWrapper requestWrapper = new RequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        try {
            logRequest(requestWrapper);
            filterChain.doFilter(requestWrapper, wrappedResponse);
        } finally {
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
            MDC.clear();

        }
    }

    private void logRequest(RequestWrapper request) throws IOException {
        String queryString = request.getQueryString();
        String clientIP = getClientIP(request);
        String uri = request.getRequestURI() + (queryString != null ? "?" + queryString : "");

        MDC.put("server", "WANTED_MARKET_API");
        MDC.put("traceId", UUID.randomUUID().toString());
        MDC.put("uri", uri);
        MDC.put("user", SecurityUtils.currentUserEmailForLogging());
        MDC.put("queryString", queryString != null ? queryString : "");
        MDC.put("clientIP", clientIP);
        MDC.put("method", request.getMethod());
        MDC.put("content-type",  request.getContentType());

        log.info("uri =[{}], headers=[{}] body =[{}]",
                uri,
                getHeaders(request),
                getRequestBody(request)
        );

    }


    private void logResponse(ContentCachingResponseWrapper response) {

        MDC.put("status",  String.valueOf(response.getStatus()));
        MDC.put("content-type",  response.getContentType());

        log.info("[{}]", logPayload(response.getContentType(), response.getContentInputStream()));
    }

    private String getRequestBody(RequestWrapper request) throws IOException {

        String contentType = request.getContentType();
        InputStream inputStream = request.getInputStream();

        boolean visible = isVisible(MediaType.valueOf(contentType == null ? "application/json" : contentType));
        if (visible) {
            byte[] content = StreamUtils.copyToByteArray(inputStream);
            if (content.length > 0) {
                return new String(content);
            }
        }
        return "";
    }



    private String logPayload(String contentType, InputStream inputStream) {
        boolean visible = isVisible(MediaType.valueOf(contentType == null ? "application/json" : contentType));
        if (visible) {
            byte[] content = new byte[0];
            try {
                content = StreamUtils.copyToByteArray(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (content.length > 0) {
                return new String(content, StandardCharsets.UTF_8);
            }
        }
        return "";
    }

    private boolean isVisible(MediaType mediaType) {
        final List<MediaType> VISIBLE_TYPES = Arrays.asList(
                MediaType.valueOf("text/*"),
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+json"),
                MediaType.valueOf("application/*+xml"),
                MediaType.MULTIPART_FORM_DATA
        );

        return VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getClientIP(HttpServletRequest request) {
        String clientIP = request.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty() || "unknown".equalsIgnoreCase(clientIP)) {
            return request.getRemoteAddr();
        }
        return clientIP.split(",")[0];
    }

}
