package com.mjc.school.service.dto;

public class AuthorDtoRequest {
    private Long id;
    private String name;

    public AuthorDtoRequest() {
    }

    public AuthorDtoRequest(String name) {
        this.name = name;
    }

    public AuthorDtoRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}