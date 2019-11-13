package com.erebor.tomkins.pos.data.ui;

public class UserUiModel {
    private int id;
    private String username;
    private String name;
    private String position;

    public UserUiModel() {
    }

    public UserUiModel(int id, String username, String name, String position) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
