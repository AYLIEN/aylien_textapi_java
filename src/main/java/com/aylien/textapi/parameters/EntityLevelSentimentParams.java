/**
 * Copyright 2018 Aylien, Inc. All Rights Reserved.
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


public class EntityLevelSentimentParams {
    private String text;
    private URL url;

    /**
     * Constructs parameters that define a document whose entities needs to
     * be analyzed.
     *
     * @param text Text to analyze
     *             This argument may be null, in which case url can not
     *             be null
     * @param url URL to analyze
     *            This argument may be null, in which case text can not
     *            be null
     */

    public EntityLevelSentimentParams(String text, URL url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }


    /**
     * Builder class to construct an EntityLevelSentimentParams instance.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String text;
        private URL url;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUrl(URL url) {
            this.url = url;
            return this;
        }

        public EntityLevelSentimentParams build() {
            return new EntityLevelSentimentParams(text, url);
        }
    }

}
