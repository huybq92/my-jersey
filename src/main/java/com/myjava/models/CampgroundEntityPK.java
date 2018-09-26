package com.myjava.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CampgroundEntityPK implements Serializable {
    private int campId;
    private int userUserId;

    @Column(name = "camp_id", nullable = false)
    @Id
    public int getCampId() {
        return campId;
    }

    public void setCampId(int campId) {
        this.campId = campId;
    }

    @Column(name = "USER_user_id", nullable = false)
    @Id
    public int getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CampgroundEntityPK that = (CampgroundEntityPK) o;

        if (campId != that.campId) return false;
        if (userUserId != that.userUserId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = campId;
        result = 31 * result + userUserId;
        return result;
    }
}
