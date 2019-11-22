package com.example.word.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity
public class Question {

    @NonNull
    @PrimaryKey
    private int id;
    private String title;
    private String a;
    private String b;
    private String c;
    private String d;
    private String ok;

    public Question(JSONObject obj) {
        id = obj.optInt("id");
        title = obj.optString("title");
        a = obj.optString("a");
        b = obj.optString("b");
        c = obj.optString("c");
        d = obj.optString("d");
        ok = obj.optString("ok");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
