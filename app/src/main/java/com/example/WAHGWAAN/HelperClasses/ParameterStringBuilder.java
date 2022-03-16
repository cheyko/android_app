package com.example.WAHGWAAN.HelperClasses;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params){
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0,resultString.length() -1) : resultString;
    }
}
