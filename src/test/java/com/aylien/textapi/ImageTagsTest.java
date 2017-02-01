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

import com.aylien.textapi.responses.ImageTag;
import com.aylien.textapi.responses.ImageTags;
import com.aylien.textapi.parameters.ImageTagsParams;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.fixture;

public class ImageTagsTest extends Fixtures {
    @Test
    public void validLogoURL() throws Exception {
        URL url = new URL("https://developer.aylien.com/images/logo-small.png");
        String body = fixture("imageTags/logo_url.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ImageTags response = textAPIClient.imageTags(new ImageTagsParams(url));
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals(url.toString(), requestParams.get("url"));
        Assert.assertEquals(27, response.getTags().size());
        ImageTag first = response.getTags().get(0);
        Assert.assertEquals("symbol", first.getName());
    }

    @Test
    public void noUrl() {
        try {
            textAPIClient.imageTags(new ImageTagsParams(null));
            Assert.fail("should fail");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (TextAPIException e) {
            Assert.fail("shouldn't fail here");
        }
    }
}
