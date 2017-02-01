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

import com.aylien.textapi.parameters.HashTagsParams;
import com.aylien.textapi.responses.HashTags;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.fixture;

public class HashTagsTest extends Fixtures {
    @Test
    public void en() throws Exception {
        String url = "http://www.bbc.com/news/science-environment-30097648";
        String body = fixture("hashtags/en.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        HashTagsParams.Builder builder = HashTagsParams.newBuilder();
        builder.setUrl(new URL(url));
        HashTags response = textAPIClient.hashtags(builder.build());
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals(url, requestParams.get("url"));
        Assert.assertEquals("en", requestParams.get("language"));
        Assert.assertEquals(16, response.getHashtags().length);
        boolean foundESA = false;
        for (String h: response.getHashtags()) {
            foundESA = h.equals("#EuropeanSpaceAgency");
            if (foundESA) break;
        }
        Assert.assertTrue(foundESA);
    }
}
