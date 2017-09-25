package hello.domain;

import java.io.Serializable;

/**
 * Created by amsaborit on 07/07/2017.
 */
public class News implements Serializable {

    private String description;
    private String title;
    private String pubDate;
    private String content;
    private String link;


    public News(String description, String title, String pubDate, String content, String link) {
        this.description = description;
        this.title = title;
        this.pubDate = pubDate;
        this.content = content;
        this.link = link;


    }
    public News(String title, String pubDate, String content) {
        this.title = title;
        this.pubDate = pubDate;
        this.content = content;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
