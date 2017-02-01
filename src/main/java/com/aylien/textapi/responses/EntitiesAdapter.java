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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class EntitiesAdapter extends XmlAdapter<AdaptedEntity, Entity> {

    @Override
    public Entity unmarshal(AdaptedEntity v) throws Exception {
        TypedEntity e = new TypedEntity();
        e.setType(v.getType());
        e.setSurfaceForms(v.sfs);

        return e;
    }

    @Override
    public AdaptedEntity marshal(Entity v) throws Exception {
        throw new NotImplementedException();
    }
}
