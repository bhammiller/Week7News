package com.example.demo.Models;

import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    private Boolean favStatus;
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

    public Boolean getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(Boolean favStatus) {
        this.favStatus = favStatus;
    }

    // Constructors

    // Connection to AppUser
    @ManyToMany(mappedBy = "categoryList")
    private List<AppUser> appUserList;

    public Category() {
    }

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

    public void setAppUserList(List<AppUser> appUserList) {
        this.appUserList = appUserList;
    }
    public Iterable<Article> findArticlesByCategory(String catName1){
        ArrayList<Article> personalArticles = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String catName = catName1.toLowerCase();
        Headlines headlines=new Headlines();
        if (catName.equalsIgnoreCase("business")){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=business&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                Headlines.class);
        }
        else if (catName.equalsIgnoreCase("entertainment")==true){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=entertainment&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
        }
        else if (catName.equalsIgnoreCase("general")){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=general&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
        }
        else if (catName.equalsIgnoreCase("health")){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=health&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
        }
        else if (catName.equalsIgnoreCase("science")){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=science&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
        }
        else if (catName.equalsIgnoreCase("sports")){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=sports&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
        }
        else if (catName.equalsIgnoreCase("technology")){
            headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?category=technology&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
        }
        else {
            headlines=restTemplate.getForObject("https://newsapi.org/v2/everything?q="+catName+"&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                Headlines.class);}

        for (Article article:headlines.getArticles()) {
            personalArticles.add(article);

        }
        return personalArticles;
    }
}
