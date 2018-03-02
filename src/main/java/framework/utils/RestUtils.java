package framework.utils;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestUtils {
    public static HttpHeaders getSpringHeaders(Map<String, List<String>> headers) {
        HttpHeaders springHeaders = new HttpHeaders();
        if (headers == null)
            return springHeaders;
        for (Map.Entry<String, List<String>> entry : headers.entrySet())
            springHeaders.put(entry.getKey(), entry.getValue());
        return springHeaders;
    }

    public static Map<String, List<String>> getLocalHeaders(Map<String, List<String>> headers) {
        Map<String, List<String>> localHeaders = new HashMap<String, List<String>>();
        if (headers == null)
            return localHeaders;
        for (Map.Entry<String, List<String>> entry : headers.entrySet())
            localHeaders.put(entry.getKey(), entry.getValue());
        return localHeaders;
    }
}
