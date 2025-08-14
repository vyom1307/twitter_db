package com.example.demo;

public class UserPublicDTO {
    Long Id;
    String email;
    String name;

    public UserPublicDTO(Long id, String email, String name) {
        Id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
