package com.example.th2;

import java.io.Serializable;

public class Task  implements Serializable {
    private int id;
    private String name;
    private String content;
    private String date;
    private String status;
    private int isColab;

    public Task() {

    }

    public Task(int id, String name, String content, String date, String status, int isColab) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
        this.status = status;
        this.isColab = isColab;
    }

    public Task(String name, String content, String date, String status, int isColab) {
        this.name = name;
        this.content = content;
        this.date = date;
        this.status = status;
        this.isColab = isColab;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getColab() {
        return isColab;
    }

    public void setColab(int colab) {
        isColab = colab;
    }
}
