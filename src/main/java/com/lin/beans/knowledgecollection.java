package com.lin.beans;

import java.util.Date;

public class knowledgecollection {
    private Integer id;

    private Integer isValid;

    private Integer userId;

    private Integer knowledgeId;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "knowledgecollection{" +
                "id=" + id +
                ", isValid=" + isValid +
                ", userId=" + userId +
                ", knowledgeId=" + knowledgeId +
                ", time=" + time +
                '}';
    }
}