package framework.rest;

import framework.enums.RestMethod;
import framework.utils.QaException;
import framework.utils.RestEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RestRequestStructure {

    private String url;
    private RestMethod method;
    private Map<String, List<String>> headers;
    private String body;

    public RestRequestStructure() {
    }

    public RestRequestStructure(String url, RestMethod method) {
        this.url = url;
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(RestMethod method) {
        this.method = method;
    }

    public void putHeader(String headerName, String... headerParams) {
        headers.put(headerName, Arrays.asList(headerParams));
    }

    public void addHeader(String headerName, String... headerParams) {
        if (this.headers.containsKey(headerName))
            throw new QaException("Requested header " + headerName + " already exists, try to rewrite it's value with putHeader method or add new value with addHeaderValues method");
        this.putHeader(headerName, headerParams);
    }

    public void addHeaderValues(String headerName, String... headerParams) {
        if (!this.headers.containsKey(headerName))
            throw new QaException("Requested header " + headerName + " doesn't exist, you can add new header using addHeader or putHeader methods");
        List<String> headerOptions = new ArrayList<String>();
        headerOptions.addAll(headers.get(headerName));
        headerOptions.addAll(Arrays.asList(headerParams));
        headers.put(headerName, headerOptions);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public RestMethod getMethod() {
        return method;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public RestResponseStructure executeBasic() {
        return RestEngine.sendRequest(this);
    }

    public String getReportMessageHeader() {
        return String.format("Method: %s URL: %s", this.method.toString(), this.url);
    }

    public String getReportMessageBody() {
        StringBuilder sb = new StringBuilder("HEADERS:\n");
        if (this.headers != null)
            for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                sb.append(entry.getKey()).append(": ");
                for (String listElement : entry.getValue())
                    sb.append(listElement).append(";");
                sb.append(sb.length() - 1);
                sb.append("\n");
            }
        sb.append("BODY:\n");
        if (this.body != null)
            sb.append(this.body).append("\n");
        return sb.toString();
    }
}