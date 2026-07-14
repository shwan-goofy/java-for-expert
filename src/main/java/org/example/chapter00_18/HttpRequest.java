package org.example.chapter00_18;

public class HttpRequest {
    private final String url;
    private final String method;

    private HttpRequest(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String toString() {
        return method + " " + url;
    }

    // static 중첩 클래스 — HttpRequest 인스턴스 없이 생성 가능
    public static class Builder {
        private String url;
        private String method = "GET";

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(url, method);
        }
    }
}
