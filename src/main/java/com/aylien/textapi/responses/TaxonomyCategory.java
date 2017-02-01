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
public class TaxonomyCategory {
    private String label;
    private String id;
    private double score;
    private boolean confident;
    @XmlElement(name="link")
    private TaxonomyCategoryLink[] links;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isConfident() {
        return confident;
    }

    public void setConfident(boolean confident) {
        this.confident = confident;
    }

    public TaxonomyCategoryLink[] getLinks() {
        return links;
    }

    public void setLinks(TaxonomyCategoryLink[] links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "TaxonomyCategory{" +
                "label='" + label + '\'' +
                ", id='" + id + '\'' +
                ", score=" + score +
                ", confident=" + confident +
                ", links=" + Arrays.toString(links) +
                '}';
    }
}
