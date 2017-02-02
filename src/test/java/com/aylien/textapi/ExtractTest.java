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

import com.aylien.textapi.parameters.ExtractParams;
import com.aylien.textapi.responses.Article;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.*;

public class ExtractTest extends Fixtures {
    @Test
    public void aylienTextAPI() throws Exception {
        String url = "http://aylien.com/text-api";
        String body = fixture("extract/aylien.com.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ExtractParams extractParams = new ExtractParams(null, new URL(url), true);
        Article article = textAPIClient.extract(extractParams);
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> requestParameters = parameters(request.getUtf8Body());
        Assert.assertEquals("true", requestParameters.get("best_image"));
        Assert.assertEquals(url, requestParameters.get("url"));
        Assert.assertEquals(0, article.getVideos().length);
        Assert.assertEquals(0, article.getFeeds().length);
        Assert.assertEquals("http://aylien.com/images/social-media-alien-big.png", article.getImages()[0]);
        Assert.assertEquals("Text Analysis API", article.getTitle());
        Assert.assertTrue(article.getArticle().startsWith("At the top") &&
                article.getArticle().endsWith("its precision.")
        );
    }

    @Test
    public void bunchOfVideos() throws Exception {
        String url = "http://feeds.mashable.com/~r/Mashable/~3/O9CysdMtUu0/";
        String body = fixture("extract/bunch_of_videos.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ExtractParams extractParams = new ExtractParams(null, new URL(url), false);
        Article article = textAPIClient.extract(extractParams);
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> requestParameters = parameters(request.getUtf8Body());
        Assert.assertEquals("false", requestParameters.get("best_image"));
        Assert.assertEquals(url, requestParameters.get("url"));
        Assert.assertEquals(50, article.getVideos().length);
        Assert.assertEquals("http://feeds.mashable.com/Mashable", article.getFeeds()[0]);
    }

    @Test
    public void publishDate() throws Exception {
        String url = "http://www.cnet.com/news/google-amp-wants-to-turbocharge-articles-loading-on-phones/";
        String body = fixture("extract/publish_date.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ExtractParams extractParams = new ExtractParams(null, new URL(url), false);
        Article article = textAPIClient.extract(extractParams);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2016, Calendar.FEBRUARY, 23, 0, 0, 0);
        Assert.assertEquals(cal.getTime(), article.getPublishDate());
    }

    @Test
    public void bunchOfKeywords() throws Exception {
        String url = "https://www.irishtimes.com/life-and-style/motors/which-brands-do-irish-car-owners-least-consider-buying-1.2953956";
        String body = fixture("extract/bunch_of_keywords.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ExtractParams extractParams = new ExtractParams(null, new URL(url), false);
        Article article = textAPIClient.extract(extractParams);
        Assert.assertEquals(21, article.getTags().length);
    }

}
