package framework.rest;

import java.util.List;
import java.util.Map;

public class RestResponseStructure {
    private int code;
    private Map<String, List<String>> headers;
    private String body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReportMessageBody() {
        StringBuilder sb = new StringBuilder("STATUS CODE: ");
        sb.append(this.getCode()).append("\n");
        sb.append("HEADERS:\n");
        if (this.headers != null)
            for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                sb.append(entry.getKey()).append(": ");
                for (String listElement : entry.getValue())
                    sb.append(listElement).append(";");
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
            }
        sb.append("BODY:\n");
        if (this.body != null)
            sb.append(this.body).append("\n");
        return sb.toString();
    }
}
