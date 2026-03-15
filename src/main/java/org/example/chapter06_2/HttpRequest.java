package org.example.chapter06_2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 정적 중첩 Builder 패턴으로 생성되는 불변 HTTP 요청 객체.
 *
 * <p>생성자를 {@code private}으로 막고 {@link Builder}를 통해서만 인스턴스를 만들 수 있다.
 * Builder는 HttpRequest 인스턴스 상태가 아닌 타입 정보만 필요하므로 {@code static}이 적합하다.
 */
public class HttpRequest {

    private final String url;
    private final String method;
    private final Map<String, String> headers;
    private final String body;

    /** Builder를 통해서만 생성된다. */
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
        this.body = builder.body;
    }

    /**
     * HttpRequest를 단계적으로 구성하는 정적 중첩 빌더.
     *
     * <p>정적 중첩 클래스이므로 {@code new HttpRequest.Builder()} 처럼
     * HttpRequest 인스턴스 없이 바로 생성할 수 있다.
     */
    public static class Builder {

        private String url;
        private String method = "GET";
        private final Map<String, String> headers = new HashMap<>();
        private String body;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        /** 설정된 값을 검증하고 HttpRequest를 생성한다. */
        public HttpRequest build() {
            if (url == null || url.isBlank()) {
                throw new IllegalStateException("url은 필수 값입니다.");
            }
            return new HttpRequest(this);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                (body != null ? ", body='" + body + '\'' : "") +
                '}';
    }
}
