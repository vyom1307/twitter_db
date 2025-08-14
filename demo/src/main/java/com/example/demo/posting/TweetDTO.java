package com.example.demo.posting;

import com.example.demo.UserPublicDTO;

public class TweetDTO {
    UserPublicDTO userDTO;
    String content;
    Long id;

    public TweetDTO(UserPublicDTO userDTO, String content, Long id) {
        this.userDTO = userDTO;
        this.content = content;
        this.id = id;
    }

    public UserPublicDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserPublicDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
