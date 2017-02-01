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
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

public class AuthTest extends Fixtures {
    @Test
    public void emptyKeyAndId() {
        try {
            new TextAPIClient("", "");
            Assert.fail("should fail");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void invalidKeyOrId() {
        try {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(403).setBody("Authentication failed"));
            textAPIClient.related(new RelatedParams("android", 20));
            Assert.fail("should fail");
        } catch (TextAPIException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Authentication failed");
        }
    }

    @Test
    public void authParamsAreSent() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("<result></result>"));
        textAPIClient.related(new RelatedParams("android", 20));
        RecordedRequest request = mockWebServer.takeRequest();
        Assert.assertEquals(request.getHeader("X-AYLIEN-TextAPI-Application-ID"), "test_id");
        Assert.assertEquals(request.getHeader("X-AYLIEN-TextAPI-Application-Key"), "test_key");
    }
}
