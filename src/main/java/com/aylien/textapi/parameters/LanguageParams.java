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

public class LanguageParams {
    private String text;
    private URL url;

    /**
     * Constructs parameters that define a document whose language needs to
     * be calculated.
     *
     * @param text Text to determine the language
     *             This argument may be null, in which case url can not
     *             be null
     * @param url URL to determine the language
     *            This argument may be null, in which case text can not
     *            be null
     */
    public LanguageParams(String text, URL url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder class to construct a LanguageParams instance.
     */
    public static class Builder {
        private String text;
        private URL url;

        public void setText(String text) {
            this.text = text;
        }

        public void setUrl(URL url) {
            this.url = url;
        }

        public LanguageParams build() {
            return new LanguageParams(text, url);
        }
    }
}
