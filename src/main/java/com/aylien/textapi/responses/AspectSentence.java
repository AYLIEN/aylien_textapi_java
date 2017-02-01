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
public class AspectSentence {
    private String text;
    private String polarity;

    @XmlElement(name="polarity_confidence")
    private double polarityConfidence;

    @XmlElementWrapper(name="aspects")
    @XmlElement(name="aspect")
    private Aspect[] aspects;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public double getPolarityConfidence() {
        return polarityConfidence;
    }

    public void setPolarityConfidence(double polarityConfidence) {
        this.polarityConfidence = polarityConfidence;
    }

    public Aspect[] getAspects() {
        return aspects;
    }

    public void setAspects(Aspect[] aspects) {
        this.aspects = aspects;
    }

    @Override
    public String toString() {
        return "AspectSentence{" +
                "text='" + text + '\'' +
                ", polarity='" + polarity + '\'' +
                ", polarityConfidence=" + polarityConfidence +
                ", aspects=" + Arrays.toString(aspects) +
                '}';
    }
}
