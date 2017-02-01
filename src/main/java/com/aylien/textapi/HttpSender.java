/**
 * Copyright 2017 Aylien, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aylien.textapi;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpSender {
    private static final String USER_AGENT = "Aylien Text API Java";

    private Map<String, List<String>> responseHeaders;

    public String post(String url, Map<String, List<String>> parameters, Map<String, String> headers)
            throws IOException, TextAPIException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            for (Map.Entry<String, String> header: headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            StringBuilder payload = new StringBuilder();
            for (Map.Entry<String, List<String>> e: parameters.entrySet()) {
                if (payload.length() > 0) { payload.append('&'); }
                String key = URLEncoder.encode(e.getKey(), "UTF-8");
                List<String> values = e.getValue();
                for (int i = 0; i < values.size(); i++) {
                    if (i > 0) { payload.append('&'); }
                    payload.append(key).append('=').append(URLEncoder.encode(values.get(i), "UTF-8"));
                }
            }

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(payload.toString());
            writer.close();

            responseHeaders = connection.getHeaderFields();

            InputStream inputStream = connection.getResponseCode() == 200
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (connection.getResponseCode() >= 300) {
                extractTextAPIError(response.toString());
            }

            return response.toString();
        } catch (MalformedURLException e) {
            throw new IOException(e);
        }
    }

    public Map<String, List<String>> getLastResponseHeaders() {
        return responseHeaders;
    }

    void extractTextAPIError(String response) throws TextAPIException {
        try {
            JSONObject result = new JSONObject(response);
            if (result.has("error")) {
                throw new TextAPIException(result.getString("error"));
            }
        } catch (org.json.JSONException e) {
            throw new TextAPIException(response);
        }
    }
}
