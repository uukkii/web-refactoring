package ru.netology;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Request {
    public static MultiMap getQueryParams(String name) {
        MultiMap parameter = new MultiValueMap();
        List<NameValuePair> params;
        try {
            params = URLEncodedUtils.parse(new URI(name), "UTF-8");
            for (NameValuePair param : params) {
                if (param.getName() != null && param.getValue() != null)
                    parameter.put(param.getName(), param.getValue());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return parameter;
    }

    public static String getQueryParamsPath(String name) {
        String result;
        int i = name.indexOf("?");
        if (i == -1) {
            return name;
        }
        result = name.substring(0, i);
        return result;
    }
}
