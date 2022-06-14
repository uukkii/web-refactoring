package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String requestLine;
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private final BufferedReader in;

    public Request(BufferedReader in) throws IOException {
        this.in = in;
        String requestLine = in.readLine();
        if (requestLine != null) {
            this.requestLine = requestLine;
            String[] requestLineSplit = requestLine.split(" ");
            this.method = requestLineSplit[0];
            if (requestLineSplit[1].contains("?")) {
                String[] pathSplit = requestLineSplit[1].split("\\?");
                this.path = pathSplit[0];
            } else {
                this.path = requestLineSplit[1];
            }
            this.params = addParams(requestLine);
            String headerName;
            String headerValue;
            Map<String, String> headers = new HashMap<>();
            while (!requestLine.isEmpty()) {
                int indexOfSplit = requestLine.indexOf(':');
                headerName = requestLine.substring(0, indexOfSplit);
                headerValue = requestLine.substring(indexOfSplit + 2);
                headers.put(headerName, headerValue);
            }
            this.headers = headers;
        }
    }

    public Map<String, String> getQueryParams() {
        return getParams();
    }

    public String getQueryParam(String name) {
        Map<String, String> params = getQueryParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static Map<String, String> addParams(String requestLine) {
        Map<String, String> parameters = new HashMap<>();
        List<NameValuePair> params;
        params = URLEncodedUtils.parse(URI.create(requestLine), "UTF-8");
        for (NameValuePair param : params) {
            if (param.getName() != null && param.getValue() != null)
                parameters.put(param.getName(), param.getValue());
        }
        return parameters;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public BufferedReader getIn() {
        return in;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
