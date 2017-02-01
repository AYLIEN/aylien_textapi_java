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

public class UnsupervisedClassifyParams {
    private String text;
    private URL url;
    private String[] classes;
    private int numberOfConcepts;

    /**
     * Constructs parameters that define a document whose needs to be classified.
     *
     * @param text Text to classify
     *             This argument may be null, in which case url can not
     *             be null
     * @param url  URL to classify
     *             This argument may be null, in which case text can not
     *             be null
     * @param classes List of classes to classify into
     * @param numberOfConcepts Number of concepts used to measure the semantic
     *                         similarity between two words.
     */
    public UnsupervisedClassifyParams(String text, URL url, String[] classes, int numberOfConcepts) {
        this.text = text;
        this.url = url;
        this.classes = classes;
        this.numberOfConcepts = numberOfConcepts;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String[] getClasses() {
        return classes;
    }

    public void setClasses(String[] classes) {
        this.classes = classes;
    }

    public int getNumberOfConcepts() {
        return numberOfConcepts;
    }

    public void setNumberOfConcepts(int numberOfConcepts) {
        this.numberOfConcepts = numberOfConcepts;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder class to construct a UnsupervisedClassifyParams instance.
     */
    public static class Builder {
        private String text;
        private URL url;
        private String[] classes;
        private int numberOfConcepts;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setUrl(URL url) {
            this.url = url;
            return this;
        }

        public Builder setClasses(String[] classes) {
            this.classes = classes;
            return this;
        }

        public Builder setNumberOfConcepts(int numberOfConcepts) {
            this.numberOfConcepts = numberOfConcepts;
            return this;
        }

        public UnsupervisedClassifyParams build() {
            return new UnsupervisedClassifyParams(text, url, classes, numberOfConcepts);
        }
    }
}
