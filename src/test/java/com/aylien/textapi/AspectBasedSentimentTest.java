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

import com.aylien.textapi.parameters.AspectBasedSentimentParams;
import com.aylien.textapi.responses.AspectsSentiment;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Test;
import org.junit.Assert;

import java.net.URL;

import static com.aylien.textapi.FixturesHelpers.*;

public class AspectBasedSentimentTest extends Fixtures {
    @Test
    public void textWithRestaurants() throws Exception {
        String text = "Delicious food. Disappointing service.";
        AspectBasedSentimentParams.StandardDomain domain = AspectBasedSentimentParams.StandardDomain.RESTAURANTS;
        String body = fixture("aspectBasedSentiment/restaurants_text.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        AspectBasedSentimentParams.Builder builder = AspectBasedSentimentParams.newBuilder();
        builder.setText(text);
        builder.setDomain(domain);
        AspectsSentiment response = textAPIClient.aspectBasedSentiment(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Assert.assertEquals("/absa/restaurants", request.getPath());
        Assert.assertEquals(2, response.getSentences().length);
        Assert.assertEquals(2, response.getAspects().length);
    }

    @Test
    public void domainPath() throws Exception {
        URL url = new URL("https://example.com/");
        for (AspectBasedSentimentParams.StandardDomain domain: AspectBasedSentimentParams.StandardDomain.values()) {
            mockWebServer.enqueue(new MockResponse().setBody("<result></result>"));
            AspectBasedSentimentParams.Builder builder = AspectBasedSentimentParams.newBuilder();
            builder.setUrl(url);
            builder.setDomain(domain);
            textAPIClient.aspectBasedSentiment(builder.build());
            RecordedRequest request = mockWebServer.takeRequest();
            Assert.assertEquals("/absa/" + domain.toString(), request.getPath());
        }
    }

}
