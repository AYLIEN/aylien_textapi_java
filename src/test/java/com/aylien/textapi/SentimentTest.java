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

import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.Sentiment;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.fixture;
import static com.aylien.textapi.FixturesHelpers.parameters;

public class SentimentTest extends Fixtures {
    @Test
    public void emptyResult() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("<result></result>"));
        SentimentParams.Builder builder = SentimentParams.newBuilder();
        builder.setText("John is a very good football player!");
        Sentiment resp = textAPIClient.sentiment(builder.build());
        Assert.assertNull(resp.getPolarity());
    }

    @Test
    public void positiveURL() throws Exception {
        String text = "John is a very good football player.";
        String body = fixture("sentiment/positive_text.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        Sentiment response = textAPIClient.sentiment(new SentimentParams(text, null, "tweet"));
                RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> params = parameters(request.getUtf8Body());
        Assert.assertEquals(params.get("mode"), "tweet");
        Assert.assertEquals(params.get("text"), text);
        Assert.assertEquals(response.getPolarity(), "positive");
        Assert.assertEquals(response.getSubjectivity(), "subjective");
        Assert.assertTrue(response.getPolarityConfidence() > 0.99);
        Assert.assertTrue(response.getSubjectivityConfidence()> 0.91);
    }

    @Test
    public void requestParams() throws Exception {
        String url = "http://news.yahoo.com/canadian-psycho-convicted-first-degree-murder-163542508.html";
        String body = fixture("sentiment/negative_url.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        SentimentParams.Builder builder = SentimentParams.newBuilder();
        builder.setUrl(new URL(url)).setMode("document");
        Sentiment response = textAPIClient.sentiment(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Assert.assertEquals(request.getPath(), "/sentiment");
        Assert.assertEquals(request.getMethod(), "POST");
        Map<String, String> params = parameters(request.getUtf8Body());
        Assert.assertEquals(params.get("mode"), "document");
        Assert.assertEquals(params.get("url"), url);
        Assert.assertEquals(response.getPolarity(), "negative");
        Assert.assertEquals(response.getSubjectivity(), "subjective");
    }

    @Test
    public void tweetModeLanguage() throws Exception {
        String text = "John is a very good football player.";
        String body = fixture("sentiment/positive_text.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        SentimentParams.Builder builder = SentimentParams.newBuilder();
        builder.setText(text);
        builder.setLanguage("es");
        Sentiment response = textAPIClient.sentiment(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> params = parameters(request.getUtf8Body());
        Assert.assertEquals("es", params.get("language"));
    }
}
