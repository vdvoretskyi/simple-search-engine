package com.conductor.test.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named
public class HttpClient {

    private final RestTemplate restTemplate;

    @Inject
    public HttpClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpResponse get(final String uri, final MultiValueMap<String, String> queryParams) {
        final HttpEntity<String> requestEntity = new HttpEntity<>("");
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        builder.queryParams(queryParams);
        final ResponseEntity<JsonResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, JsonResponse.class);
        return convert(responseEntity);
    }

    public HttpResponse post(final String uri, final Object json) {
        final HttpEntity<Object> requestEntity = new HttpEntity<>(json);
        final ResponseEntity<JsonResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, JsonResponse.class);
        return convert(responseEntity);
    }

    private static HttpResponse convert(final ResponseEntity<JsonResponse> responseEntity) {
        return new HttpResponse(responseEntity.getBody(), !responseEntity.getStatusCode().isError());
    }

    private static final class JsonResponse {
        private String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    public static final class HttpResponse {
        private final String body;
        private final boolean success;

        HttpResponse(final JsonResponse body, final boolean success) {
            this.body = body.getResponse();
            this.success = success;
        }

        public String getBody() {
            return body;
        }

        public boolean getSuccess() {
            return success;
        }
    }

}
