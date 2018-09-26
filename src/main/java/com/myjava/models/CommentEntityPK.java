package com.myjava.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CommentEntityPK implements Serializable {
    private int commentId;
    private int campgroundCampId;
    private int campgroundUserUserId;

    @Column(name = "comment_id", nullable = false)
    @Id
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Column(name = "CAMPGROUND_camp_id", nullable = false)
    @Id
    public int getCampgroundCampId() {
        return campgroundCampId;
    }

    public void setCampgroundCampId(int campgroundCampId) {
        this.campgroundCampId = campgroundCampId;
    }

    @Column(name = "CAMPGROUND_USER_user_id", nullable = false)
    @Id
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

        CommentEntityPK that = (CommentEntityPK) o;

        if (commentId != that.commentId) return false;
        if (campgroundCampId != that.campgroundCampId) return false;
        if (campgroundUserUserId != that.campgroundUserUserId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + campgroundCampId;
        result = 31 * result + campgroundUserUserId;
        return result;
    }
}
