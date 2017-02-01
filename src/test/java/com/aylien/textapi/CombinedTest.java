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

import com.aylien.textapi.parameters.CombinedParams;
import com.aylien.textapi.responses.Combined;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

import static com.aylien.textapi.FixturesHelpers.*;

public class CombinedTest extends Fixtures {
    @Test
    public void urlWith8Endpoints() throws Exception {
        URL url = new URL("http://www.bbc.com/sport/0/football/25912393");
        String body = fixture("combined/url_8_endpoints.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        String[] endpoints = {"extract","classify","concepts","entities","hashtags","language","sentiment","summarize"};
        Combined response = textAPIClient.combined(new CombinedParams(null, url, endpoints));
        RecordedRequest request = mockWebServer.takeRequest();
        Assert.assertNotNull(response.getArticle());
        Assert.assertNotNull(response.getClassifications());
        Assert.assertNotNull(response.getConcepts());
        Assert.assertNotNull(response.getEntities());
        Assert.assertNotNull(response.getHashTags());
        Assert.assertNotNull(response.getLanguage());
        Assert.assertNotNull(response.getSentiment());
        Assert.assertNotNull(response.getSummary());
    }
}
