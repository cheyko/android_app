package com.example.WAHGWAAN.HelperClasses;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JSONParser {

    public JSONObject makeHttpRequest (String Route, String method, String Body){
        URL urlObj = null;
        HttpURLConnection urlConnection = null;
        JSONObject jsonObject = null;
        String response = null;

        try {
            urlObj = new URL("https://wahgwaan.herokuapp.com" + Route);
            urlConnection = (HttpURLConnection) urlObj.openConnection();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (method.equals("POST")) {
            try {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                String payload = Body;//String.format("{\"email\":\"%s\",\"password\":\"%s\"}", Username, Password);
                //OutputStream stream = urlConnection.getOutputStream();
                //byte[] input = payload.getBytes("utf-8");
                //stream.write(input, 0, input.length);
                DataOutputStream stream = new DataOutputStream(urlConnection.getOutputStream());
                stream.writeBytes(payload);
                stream.flush();
                stream.close();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (method.equals("GET")) {
            try {
                urlConnection.setDoOutput(false);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");

                urlConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //Receive the response from the server
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream(),"utf-8");
            BufferedReader reader = new BufferedReader(in);
            StringBuilder result = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line.trim());
            }
            response = result.toString();
            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }

        try {
            jsonObject = new JSONObject(response.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jsonObject;
    }
}
