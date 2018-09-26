package com.myjava.models;

import javax.persistence.*;

@Entity
@Table(name = "comment", schema = "yelpcamp")
@IdClass(CommentEntityPK.class)
public class CommentEntity {
    private int commentId;
    private String text;
    private String author;
    private int campgroundCampId;
    private int campgroundUserUserId;

    @Id
    @Column(name = "comment_id", nullable = false)
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "text", nullable = false, length = 500)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "author", nullable = false, length = 50)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Id
    @Column(name = "CAMPGROUND_camp_id", nullable = false)
    public int getCampgroundCampId() {
        return campgroundCampId;
    }

    public void setCampgroundCampId(int campgroundCampId) {
        this.campgroundCampId = campgroundCampId;
    }

    @Id
    @Column(name = "CAMPGROUND_USER_user_id", nullable = false)
    public int getCampgroundUserUserId() {
        return campgroundUserUserId;
    }

    public void setCampgroundUserUserId(int campgroundUserUserId) {
        this.campgroundUserUserId = campgroundUserUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (commentId != that.commentId) return false;
        if (campgroundCampId != that.campgroundCampId) return false;
        if (campgroundUserUserId != that.campgroundUserUserId) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + campgroundCampId;
        result = 31 * result + campgroundUserUserId;
        return result;
    }
}
