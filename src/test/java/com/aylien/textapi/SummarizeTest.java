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

import com.aylien.textapi.parameters.SummarizeParams;
import com.aylien.textapi.responses.Summarize;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.*;

public class SummarizeTest extends Fixtures {
    @Test
    public void textWithoutTitle() {
        try {
            SummarizeParams.Builder builder = SummarizeParams.newBuilder();
            builder.setText("text");
            textAPIClient.summarize(builder.build());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("You must either provide url or a pair of text and title", e.getMessage());
        } catch (TextAPIException e) {
            Assert.fail("should not happen");
        }
    }

    @Test
    public void titleWithoutText() {
        try {
            SummarizeParams.Builder builder = SummarizeParams.newBuilder();
            builder.setTitle("title");
            textAPIClient.summarize(builder.build());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("You must either provide url or a pair of text and title", e.getMessage());
        } catch (TextAPIException e) {
            Assert.fail("should not happen");
        }
    }

    @Test
    public void noInput() {
        try {
            SummarizeParams.Builder builder = SummarizeParams.newBuilder();
            builder.setMode("short");
            textAPIClient.summarize(builder.build());
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("You must either provide url or a pair of text and title", e.getMessage());
        } catch (TextAPIException e) {
            Assert.fail("should not happen");
        }
    }

    @Test
    public void englishUrl5Sentences() throws Exception {
        String url = "http://www.bbc.com/news/science-environment-30097648";
        SummarizeParams.Builder builder = SummarizeParams.newBuilder();
        builder.setUrl(new URL(url));
        String body = fixture("summarize/en_url_5_sentences.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        Summarize summarize = textAPIClient.summarize(builder.build());
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals(url, requestParams.get("url"));
        Assert.assertEquals("5", requestParams.get("sentences_number"));
        Assert.assertEquals(5, summarize.getSentences().length);
    }

    @Test
    public void englishUrl20Percent() throws Exception {
        String url = "http://www.bbc.com/news/science-environment-30097648";
        SummarizeParams.Builder builder = SummarizeParams.newBuilder();
        builder.setUrl(new URL(url)).setPercentageOfSentences(20);
        String body = fixture("summarize/en_url_20_percent.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        Summarize summarize = textAPIClient.summarize(builder.build());
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals(url, requestParams.get("url"));
        Assert.assertEquals("20", requestParams.get("sentences_percentage"));
        Assert.assertEquals(7, summarize.getSentences().length);
        Assert.assertEquals(
                "Other analyses suggest the comet's surface is largely water-ice covered with a thin dust layer.",
                summarize.getSentences()[1]);
    }
}
