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

public class ClassifyParams {
    private String text;
    private URL url;
    private String language;

    /**
     * Constructs parameters that define a document whose classification
     * needs to be calculated.
     *
     * @param text Text to classify
     *             This argument may be null, in which case url can not
     *             be null
     * @param url URL to classify
     *            This argument may be null, in which case text can not
     *            be null
     * @param language Language of the document
     *                 This argument may be null, in which case a default
     *                 value of en is assumed
     */
    public ClassifyParams(String text, URL url, String language) {
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

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder class to construct a ClassifyParams instance.
     */
    public static class Builder {
        private String text;
        private URL url;
        private String language = "en";

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

        public ClassifyParams build() {
            return new ClassifyParams(text, url, language);
        }
    }
}
