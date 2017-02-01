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

import javax.xml.bind.annotation.*;

@XmlRootElement(name="result")
public class Combined {

    private String text;
    private Article article;
    private Concepts concepts;
    private Entities entities;
    private HashTags hashTags;
    private Language language;
    private Summarize summary;
    private Sentiment sentiment;
    private Classifications classifications;
    private TaxonomyClassifications[] taxonomyClassifications;

    @XmlElement(name="extract")
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @XmlElement(name="text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name="sentiment")
    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        sentiment.setText(text);
        this.sentiment = sentiment;
    }

    @XmlElement(name="classify")
    public Classifications getClassifications() {
        return classifications;
    }

    public void setClassifications(Classifications classifications) {
        classifications.setText(text);
        this.classifications = classifications;
    }

    @XmlElement(name="concepts")
    public Concepts getConcepts() {
        return concepts;
    }

    public void setConcepts(Concepts concepts) {
        concepts.setText(text);
        this.concepts = concepts;
    }

    @XmlElement(name="entities")
    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        entities.setText(text);
        this.entities = entities;
    }

    @XmlElement(name="hashtags")
    public HashTags getHashTags() {
        return hashTags;
    }

    public void setHashTags(HashTags hashTags) {
        hashTags.setText(text);
        this.hashTags = hashTags;
    }

    @XmlElement(name="language")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        language.setText(text);
        this.language = language;
    }

    @XmlElement(name="summarize")
    public Summarize getSummary() {
        return summary;
    }

    public void setSummary(Summarize summary) {
        summary.setText(text);
        this.summary = summary;
    }

    @XmlElement(name="classify_by_taxonomy")
    public TaxonomyClassifications[] getTaxonomyClassifications() {
        return taxonomyClassifications;
    }

    public void setTaxonomyClassifications(TaxonomyClassifications[] classifications) {
        for (TaxonomyClassifications t: classifications) {
            t.setText(text);
        }
        this.taxonomyClassifications = classifications;
    }
}
