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

import com.aylien.textapi.parameters.ClassifyParams;
import com.aylien.textapi.responses.Category;
import com.aylien.textapi.responses.Classifications;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.*;

public class ClassificationsTest extends Fixtures {
    @Test
    public void tcSoftware() throws Exception {
        String url = "http://techcrunch.com/2014/12/24/facebook-videos/";
        String body = fixture("classify/tc_software.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        Classifications response = textAPIClient.classify(new ClassifyParams(null, new URL(url), "en"));
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, String> requestParams = parameters(request.getUtf8Body());
        Assert.assertEquals(url, requestParams.get("url"));
        Assert.assertEquals("en", requestParams.get("language"));
        Assert.assertEquals("en", response.getLanguage());
        Assert.assertEquals(1, response.getCategories().length);
        Category category = response.getCategories()[0];
        Assert.assertNotNull(category);
        Assert.assertEquals("04003005", category.getSubjectCode());
        Assert.assertEquals("computing and information technology - software", category.getLabel());
        Assert.assertTrue(category.getConfidence() > 0.79);
    }
}
