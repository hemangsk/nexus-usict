package xyz.hemangkumar.rnfapp.models;

/**
 * Created by Hemang on 24/09/16.
 */

public class Event {
    String organiser;
    String detail;
    String venue;
    String date;
    String time;
    String category;
    String title;
    String contact;

    public Event() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Event(String organiser, String detail, String venue, String date, String time, String category, String title, String contact) {

        this.organiser = organiser;
        this.detail = detail;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.category = category;
        this.title = title;
        this.contact = contact;
    }

    public Event(String organiser, String detail, String venue, String date, String time, String category, String title) {
        this.organiser = organiser;
        this.detail = detail;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.category = category;
        this.title = title;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
