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

import com.aylien.textapi.parameters.LanguageParams;
import com.aylien.textapi.responses.Language;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.fixture;

public class LanguageTest extends Fixtures {
    @Test
    public void enText() throws Exception {
        String text = "Hello there, how's it going?";
        String body = fixture("language/en_text.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        Language response = textAPIClient.language(new LanguageParams(text, null));
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals(text, requestParams.get("text"));
        Assert.assertEquals("en", response.getLanguage());
        Assert.assertEquals(text, response.getText());
    }

    @Test
    public void badURL() {
        String url = "http://";
        try {
            mockWebServer.enqueue(new MockResponse().setResponseCode(400)
                    .setBody("{\"error\" : \"requirement failed: provided url is not valid.\"}"));
            textAPIClient.language(new LanguageParams(null, new URL(url)));
            Assert.fail("should fail");
        } catch (TextAPIException e) {
            Assert.assertEquals(e.getCause().getMessage(), "requirement failed: provided url is not valid.");
        } catch (MalformedURLException e) {
            // ignore
        }
    }
}
