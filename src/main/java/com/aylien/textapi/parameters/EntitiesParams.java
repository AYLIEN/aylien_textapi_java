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

public class EntitiesParams {
    private String text;
    private String language;
    private URL url;

    /**
     * Constructs parameters that define a document whose entities needs to
     * be extracted.
     *
     * @param text Text to extract entities from
     *             This argument may be null, in which case url can not
     *             be null
     * @param url URL to extract entities from
     *            This argument may be null, in which case text can not
     *            be null
     */
    public EntitiesParams(String text, URL url) {
        this(text, url, "en");
    }

    public EntitiesParams(String text, URL url, String language) {
        this.text = text;
        this.url = url;
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Builder class to construct an EntitiesParams instance.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String text;
        private String language = "en";
        private URL url;

        public void setText(String text) {
            this.text = text;
        }

        public void setUrl(URL url) {
            this.url = url;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public EntitiesParams build() {
            return new EntitiesParams(text, url, language);
        }
    }
}
