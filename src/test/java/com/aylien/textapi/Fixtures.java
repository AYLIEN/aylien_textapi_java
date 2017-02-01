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

import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.parameters;

public class Fixtures {
    MockWebServer mockWebServer;
    TextAPIClient textAPIClient;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.play();
        textAPIClient = new TextAPIClient("test_id", "test_key", false);
        textAPIClient.setApiHostAndPath(mockWebServer.getHostName() + ":" +
                mockWebServer.getPort());
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    public Map<String, String> takeRequestParams() throws IOException, InterruptedException {
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        return parameters(recordedRequest.getUtf8Body());
    }
}
