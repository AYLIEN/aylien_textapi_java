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
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Article {
    private String title;

    private String author;

    private String article;

    @XmlElementWrapper(name="images")
    @XmlElement(name="image")
    private String[] images;

    @XmlElementWrapper(name="videos")
    @XmlElement(name="video")
    private String[] videos;

    @XmlElementWrapper(name="feeds")
    @XmlElement(name="feed")
    private String[] feeds;

    @XmlElementWrapper(name="tags")
    @XmlElement(name="tag")
    private String[] tags;

    private Date publishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] image) {
        this.images = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public String[] getFeeds() {
        return feeds;
    }

    public void setFeeds(String[] feeds) {
        this.feeds = feeds;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String toString() {
        return this.title;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
