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
public class Sentiment {
    private String text;

    private String subjectivity;

    @XmlElement(name="subjectivity_confidence")
    private double subjectivityConfidence;

    private String polarity;
    @XmlElement(name="polarity_confidence")

    private double polarityConfidence;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubjectivity() {
        return subjectivity;
    }

    public void setSubjectivity(String subjectivity) {
        this.subjectivity = subjectivity;
    }

    public double getSubjectivityConfidence() {
        return subjectivityConfidence;
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

    public void setSubjectivityConfidence(double subjectivityConfidence) {
        this.subjectivityConfidence = subjectivityConfidence;
    }

    public void setPolarityConfidence(double polarityConfidence) {
        this.polarityConfidence = polarityConfidence;
    }

    public String toString() {
        return this.polarity + " (" + this.polarityConfidence + ") " +
                this.subjectivity + " (" + this.subjectivityConfidence + ")";
    }
}

