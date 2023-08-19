package com.blog.blog.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "draft_post")
public class DraftPost {

    // define fields
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private int id;

    @Column(name = "userId" )
    private int userId;

    @Column(name = "body")
    private String body;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User User;



    // define constructors
    public DraftPost(){

    }

    public DraftPost(int userId, String body, LocalDateTime createdAt, LocalDateTime updatedAt, User user) {
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        User = user;
    }

    // define setters / getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    // define to string method
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
