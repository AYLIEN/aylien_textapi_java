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
import java.util.Arrays;

@XmlAccessorType(XmlAccessType.FIELD)
public class AspectsSentiment {
    private String text;
    private String domain;

    @XmlElementWrapper(name="aspects")
    @XmlElement(name="aspect")
    private Aspect[] aspects;

    @XmlElementWrapper(name="sentences")
    @XmlElement(name="sentence")
    private AspectSentence[] sentences;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Aspect[] getAspects() {
        return aspects;
    }

    public void setAspects(Aspect[] aspects) {
        this.aspects = aspects;
    }

    public AspectSentence[] getSentences() {
        return sentences;
    }

    public void setSentences(AspectSentence[] sentences) {
        this.sentences = sentences;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "AspectsSentiment{" +
                "text='" + text + '\'' +
                ", aspects=" + Arrays.toString(aspects) +
                ", sentences=" + Arrays.toString(sentences) +
                '}';
    }
}
