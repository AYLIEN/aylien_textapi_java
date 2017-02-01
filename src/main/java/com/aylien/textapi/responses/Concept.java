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
public class Concept {
    @XmlElementWrapper(name="surfaceforms")
    private SurfaceForm[] surfaceform;

    @XmlElementWrapper(name="types")
    @XmlElement(name="type")
    private String[] types;

    private int support;

    @XmlAttribute
    private String uri;

    public SurfaceForm[] getSurfaceForms() {
        return surfaceform;
    }

    public String[] getTypes() {
        return types;
    }

    public int getSupport() {
        return support;
    }

    public String getUri() {
        return uri;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s (%d) ", uri, support));
        for (SurfaceForm sf: surfaceform) {
            builder.append(sf.getString() + " ");
        }
        for (String t: types) {
            builder.append(t + " ");
        }

        return builder.toString();
    }
}
