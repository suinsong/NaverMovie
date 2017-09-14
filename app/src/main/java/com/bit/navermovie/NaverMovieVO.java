package com.bit.navermovie;

/**
 * Created by suin on 2017-09-14.
 */

public class NaverMovieVO {

    private String title;
    private String link;
    private String image;
    private String subtitle;
    private String pubDate;
    private String director;
    private String actor;
    private String userRating;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public String getUserRating() {
        return userRating;
    }
}
