package com.example.Restinpeace.farmerassistant;

public class News_List {

    private String heading;
    private String details;
    private String image;
    private String news_link;

    public News_List(String heading, String details, String image, String news_link) {
        this.heading = heading;
        this.details = details;
        this.image = image;
        this.news_link = news_link;
    }

    public String getHeading() {
        return heading;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }

    public String getNews_link() {
        return news_link;
    }
}
