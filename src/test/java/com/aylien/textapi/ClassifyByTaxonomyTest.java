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

import com.aylien.textapi.parameters.ClassifyByTaxonomyParams;
import com.aylien.textapi.responses.TaxonomyClassifications;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

import static com.aylien.textapi.FixturesHelpers.*;

public class ClassifyByTaxonomyTest extends Fixtures {
    @Test
    public void urlWithIABQAG() throws Exception {
        URL url = new URL("http://techcrunch.com/2015/07/16/microsoft-will-never-give-up-on-mobile");
        ClassifyByTaxonomyParams.StandardTaxonomy taxonomy = ClassifyByTaxonomyParams.StandardTaxonomy.IAB_QAG;
        String body = fixture("classifyByTaxonomy/iab_url.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        ClassifyByTaxonomyParams.Builder builder = ClassifyByTaxonomyParams.newBuilder();
        builder.setUrl(url);
        builder.setTaxonomy(taxonomy);
        TaxonomyClassifications response = textAPIClient.classifyByTaxonomy(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Assert.assertEquals("/classify/iab-qag", request.getPath());
        Assert.assertEquals(response.getCategories().length, 2);
    }

    @Test
    public void emptyWithIPTCSubjectCode() throws Exception {
        URL url = new URL("http://techcrunch.com/2015/07/16/microsoft-will-never-give-up-on-mobile");
        ClassifyByTaxonomyParams.StandardTaxonomy taxonomy = ClassifyByTaxonomyParams.StandardTaxonomy.IPTC_SUBJECTCODE;
        mockWebServer.enqueue(new MockResponse().setBody("<result></result>"));
        ClassifyByTaxonomyParams.Builder builder = ClassifyByTaxonomyParams.newBuilder();
        builder.setUrl(url);
        builder.setTaxonomy(taxonomy);
        textAPIClient.classifyByTaxonomy(builder.build());
        RecordedRequest request = mockWebServer.takeRequest();
        Assert.assertEquals("/classify/iptc-subjectcode", request.getPath());
    }
}
