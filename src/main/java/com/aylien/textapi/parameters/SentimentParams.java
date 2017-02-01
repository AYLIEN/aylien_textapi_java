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

public class SentimentParams {
    private String text;
    private URL url;
    private String mode;
    private String language;

    /**
     * Constructs parameters that define a document whose sentiment need
     * analysis.
     *
     * @param text Text to calculate the sentiment of
     *             This argument may be null, in which case url can not
     *             be null
     * @param url URL to calculate the sentiment of
     *            This argument may be null, in which case text can not
     *            be null
     * @param mode Analysis mode.
     *             This argument may be null, in which case a default value
     *             of tweet is assumed.
     *             Possible values are: tweet and document.
     * @param language Input language.
     */
    public SentimentParams(String text, URL url, String mode, String language) {
        this.text = text;
        this.url = url;
        this.mode = mode;
        this.language = language;
    }

    public SentimentParams(String text, URL url, String mode) {
        this(text, url, mode, "en");
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }

    public String getMode() {
        return mode;
    }

    public String getLanguage() {
        return language;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder class that constructs a SentimentParams instance.
     */
    public static class Builder {
        private String text;
        private URL url;
        private String mode = "tweet";
        private String language = "en";

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUrl(URL url) {
            this.url = url;
            return this;
        }

        public Builder setMode(String mode) {
            this.mode = mode;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public SentimentParams build() {
            return new SentimentParams(text, url, mode, language);
        }
    }
}
