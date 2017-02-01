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

import com.aylien.textapi.parameters.ConceptsParams;
import com.aylien.textapi.responses.Concept;
import com.aylien.textapi.responses.Concepts;
import com.aylien.textapi.responses.SurfaceForm;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.*;

public class ConceptsTest extends Fixtures {
    @Test
    public void deNewsURL() throws Exception {
        String url = "http://www.zeit.de/sport/2014-12/sven-knipphals-crowdfunding-spitzensport";
        String body = fixture("concepts/de.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ConceptsParams.Builder builder = ConceptsParams.newBuilder();
        builder.setLanguage("de").setUrl(new URL(url));
        textAPIClient.concepts(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> requestParams = parameters(request.getUtf8Body());
        Assert.assertEquals(requestParams.get("url"), url);
        Assert.assertEquals(requestParams.get("language"), "de");
    }

    @Test
    public void englishText() throws Exception {
        String text = "Barack Obama is the 44th and current President " +
                "of the United States, and the first African American to hold the office.";
        String body = fixture("concepts/en.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ConceptsParams.Builder builder = ConceptsParams.newBuilder();
        builder.setText(text);
        Concepts conceptsResponse = textAPIClient.concepts(builder.build());
        RecordedRequest response = mockWebServer.takeRequest();
        Map<String, String> requestParams = parameters(response.getUtf8Body());
        Assert.assertEquals(requestParams.get("text"), text);
        Assert.assertEquals(requestParams.get("language"), "en");
        List<Concept> concepts = conceptsResponse.getConcepts();
        ArrayList<String> uris = new ArrayList<String>();
        for (Concept c: concepts) {
            uris.add(c.getUri());
        }
        Assert.assertTrue(uris.indexOf("http://dbpedia.org/resource/Barack_Obama") > -1);
        Assert.assertEquals(uris.size(), 3);
        Concept desiredConcept = null;
        for (Concept c: concepts) {
            if (c.getUri().equals("http://dbpedia.org/resource/United_States")) {
                desiredConcept = c;
            }
        }
        Assert.assertNotNull(desiredConcept);
        Assert.assertEquals(desiredConcept.getTypes().length, 5);
        Assert.assertEquals(desiredConcept.getSurfaceForms().length, 1);
        Assert.assertEquals(desiredConcept.getSupport(), 538753);
        SurfaceForm surfaceForm = desiredConcept.getSurfaceForms()[0];
        Assert.assertNotNull(surfaceForm);
        Assert.assertEquals(surfaceForm.getString(), "United States");
        Assert.assertEquals(surfaceForm.getOffset(), 54);
        Assert.assertTrue(surfaceForm.getScore() > 0.999);
        Assert.assertEquals(conceptsResponse.getLanguage(), "en");
        Assert.assertEquals(conceptsResponse.getText(), text);
    }
}
