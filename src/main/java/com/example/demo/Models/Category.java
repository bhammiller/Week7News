package com.example.demo.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String name;
    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Constructors

    // Connection to AppUser
    @ManyToMany(mappedBy = "")
    private List<AppUser> appUserList;

    public Category() {
    }

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

    public void setAppUserList(List<AppUser> appUserList) {
        this.appUserList = appUserList;
    }
}
