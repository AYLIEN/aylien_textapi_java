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
public class Aspect {
    private String name;
    private String polarity;

    @XmlElement(name="aspect_confidence")
    private double aspectConfidence;

    @XmlElement(name="polarity_confidence")
    private double polarityConfidence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public double getAspectConfidence() {
        return aspectConfidence;
    }

    public void setAspectConfidence(double aspectConfidence) {
        this.aspectConfidence = aspectConfidence;
    }

    public double getPolarityConfidence() {
        return polarityConfidence;
    }

    public void setPolarityConfidence(double polarityConfidence) {
        this.polarityConfidence = polarityConfidence;
    }

    @Override
    public String toString() {
        return "Aspect{" +
                "name='" + name + '\'' +
                ", polarity='" + polarity + '\'' +
                ", aspectConfidence=" + aspectConfidence +
                ", polarityConfidence=" + polarityConfidence +
                '}';
    }
}
