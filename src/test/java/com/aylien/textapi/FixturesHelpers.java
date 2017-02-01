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

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public class FixturesHelpers {
    private FixturesHelpers() { /* singleton */ }

    public static String fixture(String filename) throws IOException {
        return fixture(filename, "UTF8");
    }

    public static String fixture(String filename, String charset) throws IOException {
        File f = new File("src/test/resources/" + filename);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
        StringBuilder builder = new StringBuilder();
        String line = in.readLine();
        while (line != null) {
            builder.append(line);
            line = in.readLine();
        }
        in.close();

        return builder.toString();
    }

    public static Map<String, String> parameters(String body) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        String[] fields = body.split("&");
        for (String f : fields) {
            String[] field = f.split("=");
            if (field.length == 2) {
                params.put(field[0], URLDecoder.decode(field[1], "UTF8"));
            }
        }

        return params;
    }

    public static Map<String, List<String>> multiParameters(String body) throws IOException {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        String[] fields = body.split("&");
        for (String f: fields) {
            String[] field = f.split("=");
            if (field.length == 2) {
                String value = URLDecoder.decode(field[1], "UTF8");
                if (params.containsKey(field[0])) {
                    List<String> currentValue = params.get(field[0]);
                    currentValue.add(value);
                    params.put(field[0], currentValue);
                } else {
                    List<String> v = new ArrayList<String>();
                    v.add(value);
                    params.put(field[0], v);
                }
            }
        }

        return params;
    }
}
