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


import com.aylien.textapi.parameters.EntityLevelSentimentParams;
import com.aylien.textapi.responses.EntitiesSentiment;
import com.aylien.textapi.responses.Entities;
import com.aylien.textapi.responses.EntitiySentiments;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.*;

public class EntityLevelSentimentTest extends Fixtures {
    @Test
    public void englishText() throws Exception {
        String text = "Steve Jobs was a brilliant person";
        String body = fixture("elsa/en_text.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        EntityLevelSentimentParams.Builder builder = EntityLevelSentimentParams.newBuilder();
        builder.setText(text);
        EntitiesSentiment entitiesSentiment = textAPIClient.entityLevelSentiment(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> requestParams = parameters(request.getUtf8Body());
        Assert.assertEquals(requestParams.get("text"), text);
        Assert.assertEquals(entitiesSentiment.getText(), text);
        for (EntitiySentiments e: entitiesSentiment.getEntitiySentiments()){
            if(e.getTypes().equals("Organization")){
                Assert.assertEquals(e.getMentions()[0].getOffset(), 24);
                Assert.assertEquals(e.getMentions()[0].getConfidence(), 1.0, 0.001);
                Assert.assertEquals(e.getMentions()[0].getText(), "Apple");
                Assert.assertEquals(e.getMentions()[0].getSentiment().getConfidence(), 0.45, 0.001);
                Assert.assertEquals(e.getMentions()[0].getSentiment().getPolarity(), "negative");
                Assert.assertEquals(e.getOverallSentiment().getPolarity(), "negative");
                Assert.assertEquals(e.getOverallSentiment().getConfidence(), 0.45, 0.001);
                Assert.assertEquals(e.getLinks()[0].getUri(), "http://dbpedia.org/resource/Apple_Inc.");
                Assert.assertEquals(e.getLinks()[0].getProvider(), "dbpedia");
                Assert.assertEquals(e.getLinks()[0].getTypes().length, 5);
                Assert.assertEquals(e.getLinks()[0].getConfidence(), 0.57, 0.001);
            } else if(e.getTypes().equals("Person")){
                Assert.assertEquals(e.getMentions()[0].getOffset(), 0);
                Assert.assertEquals(e.getMentions()[0].getConfidence(), 1.0, 0.001);
                Assert.assertEquals(e.getMentions()[0].getText(), "Steve Jobs");
                Assert.assertEquals(e.getMentions()[0].getSentiment().getConfidence(), 0.37, 0.001);
                Assert.assertEquals(e.getMentions()[0].getSentiment().getPolarity(), "positive");
                Assert.assertEquals(e.getOverallSentiment().getPolarity(), "positive");
                Assert.assertEquals(e.getOverallSentiment().getConfidence(), 0.37, 0.001);
                Assert.assertEquals(e.getLinks()[0].getUri(), "http://dbpedia.org/resource/Steve_Jobs");
                Assert.assertEquals(e.getLinks()[0].getProvider(), "dbpedia");
                Assert.assertEquals(e.getLinks()[0].getTypes().length, 1);
                Assert.assertEquals(e.getLinks()[0].getConfidence(), 0.07, 0.001);

            }
            Assert.assertEquals(1,1);
        }
    }
}
