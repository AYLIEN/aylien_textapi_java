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

import com.aylien.textapi.parameters.MicroformatsParams;
import com.aylien.textapi.responses.Address;
import com.aylien.textapi.responses.HCard;
import com.aylien.textapi.responses.Microformats;
import com.aylien.textapi.responses.Name;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

import static com.aylien.textapi.FixturesHelpers.fixture;

public class MicroformatsTest extends Fixtures {
    @Test
    public void validURL() throws Exception {
        URL url = new URL("http://codepen.io/anon/pen/ZYaKbz.html");
        String body = fixture("microformats/full_url.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        Microformats response = textAPIClient.microformats(new MicroformatsParams(url));
        Map<String, String> requestParams = takeRequestParams();
        Assert.assertEquals(url.toString(), requestParams.get("url"));
        Assert.assertEquals(1, response.gethCards().size());
        HCard hCard = response.gethCards().get(0);
        Assert.assertEquals("sally@example.com", hCard.getEmail());
        Address address = hCard.getAddress();
        Assert.assertNotNull(address);
        Assert.assertEquals("California", address.getRegion());
        Name structuredName = hCard.getStructuredName();
        Assert.assertNotNull(structuredName);
        Assert.assertEquals("Sally", structuredName.getGivenName());
    }

}
