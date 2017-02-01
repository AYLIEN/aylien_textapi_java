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

import com.aylien.textapi.parameters.UnsupervisedClassifyParams;
import com.aylien.textapi.responses.UnsupervisedClass;
import com.aylien.textapi.responses.UnsupervisedClassifications;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static com.aylien.textapi.FixturesHelpers.*;

public class UnsupervisedClassificationsTest extends Fixtures {
    @Test
    public void textWith5Classes() throws Exception {
        String text = "The Mona Lisa is a 16th century oil painting created by Leonardo. It's held at the Louvre in Paris.";
        String[] classes = {"painting", "sculpting", "music", "theatre", "dancing"};
        String body = fixture("unsupervisedClassify/text_5_classes.xml");
        mockWebServer.enqueue(new MockResponse().setBody(body));
        UnsupervisedClassifications response = textAPIClient.unsupervisedClassify(new UnsupervisedClassifyParams(text, null, classes, 0));
        RecordedRequest request = mockWebServer.takeRequest();
        Map<String, List<String>> requestParams = multiParameters(request.getUtf8Body());
        Assert.assertEquals(text, requestParams.get("text").get(0));
        Set<String> requestClasses = new HashSet<String>(requestParams.get("class"));
        Set<String> originalClasses = new HashSet<String>(Arrays.asList(classes));
        Assert.assertEquals(true, requestClasses.equals(originalClasses));
        List<String> rClasses = new ArrayList<>();
        for (UnsupervisedClass uc: response.getClasses()) {
            rClasses.add(uc.getLabel());
        }
        Set<String> responseClasses = new HashSet<String>(rClasses);
        Assert.assertEquals(true, responseClasses.equals(originalClasses));
        UnsupervisedClass topClass = response.getClasses()[0];
        Assert.assertEquals("painting", topClass.getLabel());
    }
}
