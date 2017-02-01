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

import com.aylien.textapi.parameters.RelatedParams;
import com.aylien.textapi.responses.Phrase;
import com.aylien.textapi.responses.Related;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.fixture;

public class RelatedTest extends Fixtures {
    @Test
    public void android() throws Exception {
        String phrase = "android";
        String body = fixture("related/android.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        RelatedParams.Builder builder = RelatedParams.newBuilder();
        builder.setPhrase(phrase);
        Related response = textAPIClient.related(builder.build());
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals("20", requestParams.get("count"));
        Assert.assertEquals(phrase, requestParams.get("phrase"));
        Assert.assertEquals(20, response.getPhrases().length);
        Assert.assertEquals(phrase, response.getPhrase());
        Phrase targetPhrase = null;
        for (Phrase p: response.getPhrases()) {
            if (p.getPhrase().equals("ios")) {
                targetPhrase = p;
            }
        }
        Assert.assertNotNull(targetPhrase);
        Assert.assertTrue(targetPhrase.getDistance() > 0.87);
    }
}
