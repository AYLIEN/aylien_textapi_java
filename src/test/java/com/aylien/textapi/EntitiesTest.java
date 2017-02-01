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

import com.aylien.textapi.parameters.EntitiesParams;
import com.aylien.textapi.responses.Entity;
import com.aylien.textapi.responses.Entities;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.*;

public class EntitiesTest extends Fixtures {
    @Test
    public void englishText() throws Exception {
        String text = "The iPhone 5S (marketed with a stylized lowercase 's' " +
                "as iPhone 5s) is a smartphone developed by Apple Inc. It is " +
                "part of the iPhone line, and was released on September 20, " +
                "2013. Apple held an event to formally introduce the high-range " +
                "phone, and its mid-range counterpart, the iPhone 5C, on" +
                " September 10, 2013.";
        String body = fixture("entities/en_text.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        EntitiesParams.Builder builder = EntitiesParams.newBuilder();
        builder.setText(text);
        Entities entities = textAPIClient.entities(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> requestParams = parameters(request.getUtf8Body());
        Assert.assertEquals(requestParams.get("text"), text);
        Assert.assertEquals(entities.getText(), text);
        Assert.assertEquals(entities.getEntities().size(), 4);
        for (Entity e: entities.getEntities()) {
            if (e.getType().equals("organization")) {
                Assert.assertEquals(e.getSurfaceForms().size(), 1);
                Assert.assertTrue(e.getSurfaceForms().get(0).equals("Apple Inc."));
            } else if (e.getType().equals("keyword")) {
                Assert.assertEquals(e.getSurfaceForms().size(), 11);
            } else if (e.getType().equals("date")) {
                Assert.assertEquals(e.getSurfaceForms().size(), 2);
            } else if (e.getType().equals("product")) {
                Assert.assertEquals(e.getSurfaceForms().size(), 1);
                Assert.assertTrue(e.getSurfaceForms().get(0).equals("iPhone"));
            } else {
                Assert.fail("unexpected entity type");
            }
        }
    }
}
