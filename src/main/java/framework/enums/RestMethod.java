package framework.enums;

import org.springframework.http.HttpMethod;

public enum RestMethod {
    GET(HttpMethod.GET, "GET"),
    PUT(HttpMethod.PUT, "PUT"),
    POST(HttpMethod.POST, "POST"),
    DELETE(HttpMethod.DELETE, "DELETE");

    private HttpMethod httpMethod;
    private String methodName;

    RestMethod(HttpMethod httpMethod, String name) {
        this.httpMethod = httpMethod;
        this.methodName = name;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String toString() {
        return this.methodName;
    }
}
