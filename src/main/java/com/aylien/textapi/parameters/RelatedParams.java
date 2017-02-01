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

public class RelatedParams {
    private String phrase;
    private int count;

    /**
     * Constructs parameters that define a phrase whose related phrases need
     * to be retrieved.
     *
     * @param phrase Phrase to retrieve its related phrases
     * @param count Number of related phrases to retrieve
     *              This argument may be zero, in which case a default
     *              value of 20 is assumed. Maximum possible value is 100.
     */
    public RelatedParams(String phrase, int count) {
        this.phrase = phrase;
        this.count = count;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getCount() {
        return count;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder class to construct a RelatedParams instance.
     */
    public static class Builder {
        private String phrase;
        private int count = 20;

        public Builder setPhrase(String phrase) {
            this.phrase = phrase;
            return this;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public RelatedParams build() {
            return new RelatedParams(phrase, count);
        }
    }
}
