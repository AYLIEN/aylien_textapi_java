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
import org.junit.Assert;
import org.junit.Test;

public class RateLimitTest extends Fixtures {
    @Test
    public void ratelimits() throws Exception {
        String limit = "1000";
        String remaining = "977";
        String reset = "1419379200";

        mockWebServer.enqueue(new MockResponse()
                .setHeader("X-RateLimit-Limit", limit)
                .setHeader("X-RateLimit-Remaining", remaining)
                .setHeader("X-RateLimit-Reset", reset)
                .setBody("<result></result>")
        );
        textAPIClient.related(new RelatedParams("test", 20));
        Assert.assertEquals(textAPIClient.getRateLimits().getRemaining(), Integer.parseInt(remaining));
        Assert.assertEquals(textAPIClient.getRateLimits().getLimit(), Integer.parseInt(limit));
        Assert.assertEquals(textAPIClient.getRateLimits().getReset(), Integer.parseInt(reset));
    }
}
