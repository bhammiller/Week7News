package com.example.demo.Models;

import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.util.ArrayList;
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
    public Iterable<Article> findArticlesByCategory(String catName){
        ArrayList<Article> personalArticles = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Headlines headlines=restTemplate.getForObject("https://newsapi.org/v2/everything?q="+catName.toLowerCase()+"&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                Headlines.class);
        for (Article article:headlines.getArticles()) {
            personalArticles.add(article);

        }
        return personalArticles;
    }
}
