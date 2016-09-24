package xyz.hemangkumar.rnfapp.models;

/**
 * Created by Hemang on 18/09/16.
 */
public class Blog {
    String author;
    String text;
    String title;
    String date;
    String category;
    String picture;
    String subheading;

    public Blog(String author, String text, String title, String date, String category, String picture, String subheading) {
        this.author = author;
        this.text = text;
        this.title = title;
        this.date = date;
        this.category = category;
        this.picture = picture;
        this.subheading = subheading;
    }



    public Blog(){

    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }


}
