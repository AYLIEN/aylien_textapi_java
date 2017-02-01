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

package com.aylien.textapi.parameters;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClassifyByTaxonomyParams {

    public enum StandardTaxonomy implements Taxonomy {
        IAB_QAG,
        IPTC_SUBJECTCODE;

        public String getName() {
            return name();
        }

        public String toString() {
            return name().toLowerCase().replace("_", "-");
        }

        public static Taxonomy taxonomyFor(final String name) {
            if (taxonomies.isEmpty()) {
                for (Taxonomy taxonomy: values()) {
                    taxonomies.putIfAbsent(taxonomy.getName(), taxonomy);
                }
            }
            taxonomies.putIfAbsent(name.toString(), new Taxonomy() {
                @Override
                public String getName() {
                    return name;
                }
                public String toString() {
                    return name.toLowerCase().replace("_", "-");
                }
            });

            return taxonomies.get(name);
        }

        private static ConcurrentMap<String, Taxonomy> taxonomies
                = new ConcurrentHashMap<String, Taxonomy>();
    }

    private String text;
    private String language;
    private URL url;
    private Taxonomy taxonomy;

    public String getText() {
        return text;
    }

    public String getLanguage() {
        return language;
    }

    public URL getUrl() {
        return url;
    }

    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     *
     */
    public ClassifyByTaxonomyParams(String text, URL url, Taxonomy taxonomy, String language) {
        this.text = text;
        this.url = url;
        this.taxonomy = taxonomy;
        this.language = language;
    }

    public static class Builder {
        private String text;
        private URL url;
        private String language = "en";
        private Taxonomy taxonomy;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUrl(URL url) {
            this.url = url;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setTaxonomy(Taxonomy taxonomy) {
            this.taxonomy = taxonomy;
            return this;
        }

        public ClassifyByTaxonomyParams build() {
            return new ClassifyByTaxonomyParams(text, url, taxonomy, language);
        }

    }

}
