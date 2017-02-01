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

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

public class AdaptedEntity extends Entity {
    @XmlAttribute String type;

    @XmlElement(name="surfaceform")
    List<SurfaceForm> surfaceforms = new ArrayList<SurfaceForm>();

    List<String> sfs = new ArrayList<String>();

    public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        for (SurfaceForm v: surfaceforms) {
            sfs.add(v.surfaceform);
        }
    }

    private static class SurfaceForm {
        @XmlValue String surfaceform;
    }
}
