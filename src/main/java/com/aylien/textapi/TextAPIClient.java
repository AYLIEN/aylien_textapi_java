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

package com.aylien.textapi;

import com.aylien.textapi.parameters.*;
import com.aylien.textapi.responses.*;

import java.io.StringReader;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

public class TextAPIClient {

    private String applicationId;

    private String applicationKey;

    private boolean useHttps;

    private HttpSender httpSender;

    private String apiHostAndPath;

    private RateLimits rateLimits;

    /**
     * Constructs a Text API Client.
     *
     * @param applicationId Your application ID
     * @param applicationKey Your application key
     */
    public TextAPIClient(String applicationId, String applicationKey) {
        this(applicationId, applicationKey, true);
    }

    /**
     * Constructs a Text API Client.
     *
     * @param applicationId Your application ID
     * @param applicationKey Your application key
     * @param useHttps Whether to use HTTPS for web service calls
     */
    public TextAPIClient(String applicationId, String applicationKey, boolean useHttps) {
        if (applicationId == null || applicationId.isEmpty() ||
                applicationKey == null || applicationKey.isEmpty())
        {
            throw new IllegalArgumentException("Invalid Application ID or Application Key");
        }
        this.applicationId = applicationId;
        this.applicationKey = applicationKey;
        this.useHttps = useHttps;
        this.httpSender = new HttpSender();
        this.apiHostAndPath = "api.aylien.com/api/v1";
        this.rateLimits = new RateLimits();
    }

    public void setApiHostAndPath(String apiHostAndPath) {
        this.apiHostAndPath = apiHostAndPath;
    }

    /**
     * Extracts the main body of article, including embedded media such as
     * images &amp; videos from a URL and removes all the surrounding clutter.
     *
     * @param extractParams extract parameters
     * @return Article
     */
    public Article extract(ExtractParams extractParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (extractParams.getHtml() != null) {
            parameters.put("html", extractParams.getHtml());
        } else if (extractParams.getUrl() != null) {
            parameters.put("url", extractParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide html or url");
        }

        if (extractParams.getBestImage()) {
            parameters.put("best_image", "true");
        } else {
            parameters.put("best_image", "false");
        }

        Article article;
        try {
            String response = this.doHttpRequest("extract", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Article.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Article> root = u.unmarshal(new StreamSource(new StringReader(response)), Article.class);
            article = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return article;
    }

    /**
     * Classifies a body of text according to IPTC NewsCode standard.
     *
     * @param classifyParams classify parameters
     * @return Classifications
     */
    public Classifications classify(ClassifyParams classifyParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (classifyParams.getText() != null) {
            parameters.put("text", classifyParams.getText());
        } else if (classifyParams.getUrl() != null) {
            parameters.put("url", classifyParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        if (classifyParams.getLanguage() != null) {
            parameters.put("language", classifyParams.getLanguage());
        }

        Classifications classifications;
        try {
            String response = this.doHttpRequest("classify", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Classifications.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Classifications> root = u.unmarshal(new StreamSource(new StringReader(response)), Classifications.class);
            classifications = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return classifications;
    }

    /**
     * Classifies a body of text according to the specified taxonomy.
     *
     * @param classifyParams classify parameters
     * @return TaxonomyClassifications
     */
    public TaxonomyClassifications classifyByTaxonomy(ClassifyByTaxonomyParams classifyParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (classifyParams.getText() != null) {
            parameters.put("text", classifyParams.getText());
        } else if (classifyParams.getUrl() != null) {
            parameters.put("url", classifyParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        if (classifyParams.getTaxonomy() == null) {
            throw new IllegalArgumentException("You must specify the taxonomy");
        }

        if (classifyParams.getLanguage() != null) {
            parameters.put("language", classifyParams.getLanguage());
        }

        TaxonomyClassifications classifications;
        try {
            String response = this.doHttpRequest("classify/" + classifyParams.getTaxonomy().toString(), transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(TaxonomyClassifications.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<TaxonomyClassifications> root =
                    u.unmarshal(new StreamSource(new StringReader(response)), TaxonomyClassifications.class);
            classifications = root.getValue();
        } catch(Exception e) {
            throw new TextAPIException(e);
        }

        return classifications;
    }

    /**
     * Picks the most semantically relevant class label or tag.
     *
     * @param classifyParams classify parameters
     * @return UnsupervisedClassifications
     */
    public UnsupervisedClassifications unsupervisedClassify(UnsupervisedClassifyParams classifyParams) throws TextAPIException {
        Map<String, List<String>> parameters = new HashMap<String, List<String>>();
        if (classifyParams.getText() != null) {
            parameters.put("text", Arrays.asList(classifyParams.getText()));
        } else if (classifyParams.getUrl() != null) {
            parameters.put("url", Arrays.asList(classifyParams.getUrl().toString()));
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        parameters.put("class", Arrays.asList(classifyParams.getClasses()));

        if (classifyParams.getNumberOfConcepts() > 0) {
            parameters.put("number_of_concepts", Arrays.asList(Integer.toString(classifyParams.getNumberOfConcepts())));
        }

        UnsupervisedClassifications unsupervisedClassifications;
        try {
            String response = this.doHttpRequest("classify/unsupervised", parameters);
            JAXBContext jc = JAXBContext.newInstance(UnsupervisedClassifications.class);
            Unmarshaller u = jc.createUnmarshaller();

            unsupervisedClassifications = (UnsupervisedClassifications) u.unmarshal(new StringReader(response));
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return unsupervisedClassifications;
    }

    /**
     * Run multiple analysis operations in one API call by specifying multiple
     * endpoints.
     *
     * @param combinedParams combined parameters
     * @return Combined
     */
    public Combined combined(CombinedParams combinedParams) throws TextAPIException {
        Map<String, List<String>> parameters = new HashMap<String, List<String>>();
        if (combinedParams.getText() != null) {
            parameters.put("text", Collections.singletonList(combinedParams.getText()));
        } else if (combinedParams.getUrl() != null) {
            parameters.put("url", Collections.singletonList(combinedParams.getUrl().toString()));
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }
        parameters.put("endpoint", Arrays.asList(combinedParams.getEndpoints()));

        Combined combined;
        try {
            String response = this.doHttpRequest("combined", parameters);
            JAXBContext jc = JAXBContext.newInstance(Combined.class);
            Unmarshaller u = jc.createUnmarshaller();

            combined = (Combined) u.unmarshal(new StringReader(response));
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return combined;
    }

    /**
     * Extracts named entities mentioned in a document, disambiguates and
     * cross link them to DBPedia and Linked Data entities, along with their
     * semantic types (including DBPedia and schema.org).
     *
     * @param conceptsParams concepts parameters
     * @return Concepts
     */
    public Concepts concepts(ConceptsParams conceptsParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (conceptsParams.getText() != null) {
            parameters.put("text", conceptsParams.getText());
        } else if (conceptsParams.getUrl() != null) {
            parameters.put("url", conceptsParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        if (conceptsParams.getLanguage() != null) {
            parameters.put("language", conceptsParams.getLanguage());
        }

        Concepts concepts;
        try {
            String response = this.doHttpRequest("concepts", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Concepts.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Concepts> root = u.unmarshal(new StreamSource(new StringReader(response)), Concepts.class);
            concepts = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return concepts;
    }

    /**
     * Extracts named entities (people, organizations and locations) and values
     * (URLs, emails, telephone numbers, currency amounts and percentages)
     * mentioned in a bod of text.
     *
     * @param entitiesParams entities parameters
     * @return Entities
     */
    public Entities entities(EntitiesParams entitiesParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (entitiesParams.getText() != null) {
            parameters.put("text", entitiesParams.getText());
        } else if (entitiesParams.getUrl() != null) {
            parameters.put("url", entitiesParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide html or url");
        }

        Entities entities;
        try {
            String response = this.doHttpRequest("entities", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Entities.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Entities> root = u.unmarshal(new StreamSource(new StringReader(response)), Entities.class);
            entities = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return entities;
    }

    /**
     * Suggests hashtags describing the document.
     *
     * @param hashTagsParams hashtags parameters
     * @return HashTags
     */
    public HashTags hashtags(HashTagsParams hashTagsParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();

        if (hashTagsParams.getText() != null) {
            parameters.put("text", hashTagsParams.getText());
        } else if (hashTagsParams.getUrl() != null) {
            parameters.put("url", hashTagsParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        if (hashTagsParams.getLanguage() != null) {
            parameters.put("language", hashTagsParams.getLanguage());
        }

        HashTags hashTags;
        try {
            String response = this.doHttpRequest("hashtags", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(HashTags.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<HashTags> root = u.unmarshal(new StreamSource(new StringReader(response)), HashTags.class);
            hashTags = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return hashTags;
    }

    /**
     * Detects the main language of a document is written in.
     *
     * @param languageParams language parameters
     * @return Language
     */
    public Language language(LanguageParams languageParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (languageParams.getText() != null) {
            parameters.put("text", languageParams.getText());
        } else if (languageParams.getUrl() != null) {
            parameters.put("url", languageParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        Language language;
        try {
            String response = this.doHttpRequest("language", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Language.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Language> root = u.unmarshal(new StreamSource(new StringReader(response)), Language.class);
            language = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return language;
    }

    /**
     * Returns phrases related to the provided unigram or bigram.
     *
     * @param relatedParams related parameters
     * @return Related
     */
    public Related related(RelatedParams relatedParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (relatedParams.getPhrase() != null) {
            parameters.put("phrase", relatedParams.getPhrase());
        } else {
            throw new IllegalArgumentException("You must provide a phrase");
        }

        if (relatedParams.getCount() > 0) {
            parameters.put("count", Integer.toString(relatedParams.getCount()));
        }

        Related related;
        try {
            String response = this.doHttpRequest("related", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Related.class);
            Unmarshaller u = jc.createUnmarshaller();

            related = (Related) u.unmarshal(new StringReader(response));
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return related;
    }

    /**
     * Detects sentiment of a body of text in terms of polarity
     * ("positive" or "negative") and subjectivity
     * ("subjective" or "objective").
     *
     * @param sentimentParams sentiment parameters
     * @return Sentiment.
     */
    public Sentiment sentiment(SentimentParams sentimentParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (sentimentParams.getText() != null) {
            parameters.put("text", sentimentParams.getText());
        } else if (sentimentParams.getUrl() != null) {
            parameters.put("url", sentimentParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide url or text");
        }

        if (sentimentParams.getMode() != null) {
            parameters.put("mode", sentimentParams.getMode());
            if (sentimentParams.getMode().equals("tweet") && sentimentParams.getLanguage() != null) {
                parameters.put("language", sentimentParams.getLanguage());
            }
        }

        Sentiment sentiment;
        try {
            String response = this.doHttpRequest("sentiment", transformParameters(parameters));

            JAXBContext jc = JAXBContext.newInstance(Sentiment.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Sentiment> root = u.unmarshal(new StreamSource(new StringReader(response)), Sentiment.class);
            sentiment = root.getValue();
        } catch(Exception e) {
            throw new TextAPIException(e);
        }

        return sentiment;
    }

    /**
     * Given a review for a product or service, analyzes the sentiment of the
     * review towards each of the aspects of the product or review that are
     * mentioned in it.
     *
     * @param params aspect based sentiment analysis parameters
     * @return AspectsSentiment
     */
    public AspectsSentiment aspectBasedSentiment(AspectBasedSentimentParams params) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (params.getText() != null) {
            parameters.put("text", params.getText());
        } else if (params.getUrl() != null) {
            parameters.put("url", params.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide text or url");
        }

        if (params.getDomain() == null) {
            throw new IllegalArgumentException("You must specify the domain");
        }

        AspectsSentiment aspectsSentiment;
        try {
            String response = this.doHttpRequest("absa/" + params.getDomain().toString(), transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(AspectsSentiment.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<AspectsSentiment> root =
                    u.unmarshal(new StreamSource(new StringReader(response)), AspectsSentiment.class);
            aspectsSentiment = root.getValue();
        } catch(Exception e) {
            throw new TextAPIException(e);
        }

        return aspectsSentiment;
    }

    /**
     * Summarizes an article into a few key sentences.
     *
     * @param summarizeParams summarize params
     * @return Summarize
     */
    public Summarize summarize(SummarizeParams summarizeParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (summarizeParams.getTitle() != null && summarizeParams.getText() != null) {
            parameters.put("title", summarizeParams.getTitle());
            parameters.put("text", summarizeParams.getText());
        } else if (summarizeParams.getUrl() != null) {
            parameters.put("url", summarizeParams.getUrl().toString());
        } else {
            throw new IllegalArgumentException("You must either provide url or a pair of text and title");
        }

        if (summarizeParams.getMode() != null) {
            parameters.put("mode", summarizeParams.getMode());
        }

        if (summarizeParams.getPercentageOfSentences() > 0) {
            parameters.put("sentences_percentage", Integer.toString(summarizeParams.getPercentageOfSentences()));
        } else if (summarizeParams.getNumberOfSentences() > 0) {
            parameters.put("sentences_number", Integer.toString(summarizeParams.getNumberOfSentences()));
        }

        Summarize summarize;
        try {
            String response = this.doHttpRequest("summarize", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Summarize.class);
            Unmarshaller u = jc.createUnmarshaller();

            JAXBElement<Summarize> root = u.unmarshal(new StreamSource(new StringReader(response)), Summarize.class);
            summarize = root.getValue();
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return summarize;
    }

    /**
     * Extracts microformats
     *
     * @param microformatsParams microformats params
     * @return Microformats
     */
    public Microformats microformats(MicroformatsParams microformatsParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (microformatsParams.getUrl() == null) {
            throw new IllegalArgumentException("You must provide a url");
        }

        parameters.put("url", microformatsParams.getUrl().toString());

        Microformats microformats;
        try {
            String response = this.doHttpRequest("microformats", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(Microformats.class);
            Unmarshaller u = jc.createUnmarshaller();

            microformats = (Microformats) u.unmarshal(new StringReader(response));
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return microformats;
    }

    /**
     * Assigns relevant tags to an image
     *
     * @param imageTagsParams
     * @return ImageTags
     */
    public ImageTags imageTags(ImageTagsParams imageTagsParams) throws TextAPIException {
        Map<String, String> parameters = new HashMap<String, String>();
        if (imageTagsParams.getUrl() == null) {
            throw new IllegalArgumentException("You must provide a url");
        }

        parameters.put("url", imageTagsParams.getUrl().toString());

        ImageTags imageTags;
        try {
            String response = this.doHttpRequest("image-tags", transformParameters(parameters));
            JAXBContext jc = JAXBContext.newInstance(ImageTags.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            imageTags = (ImageTags) unmarshaller.unmarshal(new StringReader(response));
        } catch (Exception e) {
            throw new TextAPIException(e);
        }

        return imageTags;
    }

    private Map<String, List<String>> transformParameters(Map<String, String> parameters) {
        Map<String, List<String>> transformedParameters = new HashMap<String, List<String>>();
        for (Map.Entry<String, String> entry: parameters.entrySet()) {
            List<String> values = Arrays.asList(entry.getValue());
            transformedParameters.put(entry.getKey(), values);
        }

        return transformedParameters;
    }

    private String doHttpRequest(String endpoint, Map<String, List<String>> parameters) throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "text/xml");
        headers.put("X-AYLIEN-TextAPI-Application-ID", this.applicationId);
        headers.put("X-AYLIEN-TextAPI-Application-Key", this.applicationKey);

        String url = String.format("%s://%s/%s",
                (this.useHttps ? "https" : "http"),
                this.apiHostAndPath,
                endpoint
        );

        String response = this.httpSender.post(url, parameters, headers);
        Map<String, List<String>> responseHeaders = this.httpSender.getLastResponseHeaders();
        for (Map.Entry<String, List<String>> h: responseHeaders.entrySet()) {
            if (h.getKey() != null && h.getKey().startsWith("X-RateLimit-")) {
                String key = h.getKey();
                int value = Integer.parseInt(h.getValue().get(0));
                if (key.endsWith("-Limit")) this.rateLimits.setLimit(value);
                if (key.endsWith("-Remaining")) this.rateLimits.setRemaining(value);
                if (key.endsWith("-Reset")) this.rateLimits.setReset(value);
            }
        }

        return response;
    }

    public class RateLimits {
        private int limit;
        private int remaining;
        private int reset;

        public RateLimits() {
            new RateLimits(0, 0, 0);
        }

        public RateLimits(int limit, int remaining, int reset) {
            this.limit = limit;
            this.remaining = remaining;
            this.reset = reset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getRemaining() {
            return remaining;
        }

        public void setRemaining(int remaining) {
            this.remaining = remaining;
        }

        public int getReset() {
            return reset;
        }

        public void setReset(int reset) {
            this.reset = reset;
        }
    }

    public RateLimits getRateLimits() {
        return this.rateLimits;
    }
}
