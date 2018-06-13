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

package com.aylien.textapi.responses;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class EntitiySentiments {

    @XmlElementWrapper(name="mentions")
    @XmlElement(name="mention")
    private Mention[] mentions;

    @XmlElement(name="overall_sentiment")
    private EntitySentiment overallSentiment;

    @XmlElementWrapper(name="links")
    @XmlElement(name="link")
    private Links[] links;

    private String types;

    public Mention[] getMentions() {
        return mentions;
    }

    public void setMentions(Mention[] mentions) {
        this.mentions = mentions;
    }

    public EntitySentiment getOverallSentiment() {
        return overallSentiment;
    }

    public void setOverallSentiment(EntitySentiment overallSentiment) {
        this.overallSentiment = overallSentiment;
    }

    public Links[] getLinks() {
        return links;
    }

    public void setLinks(Links[] links) {
        this.links = links;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
