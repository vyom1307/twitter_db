package com.example.demo.posting;

import com.example.demo.User;
import com.example.demo.UserPublicDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(
        name = "idx_time",columnList = "dateTime"
))
public class Tweet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Tweet(){

    }

    public Tweet(String content, User user) {

        this.content = content;
        this.dateTime = LocalDateTime.now();
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
