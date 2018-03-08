package com.example.demo.Controllers;

import com.example.demo.Models.AppUser;
import com.example.demo.Models.Article;
import com.example.demo.Models.Category;
import com.example.demo.Models.Headlines;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class NewsService {

    public Iterable<Article> findTopHeadlines(){
        RestTemplate restTemplate = new RestTemplate();
        Headlines headlines=restTemplate.getForObject("https://newsapi.org/v2/top-headlines?country=us&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                Headlines.class);
        System.out.println(headlines.toString());
        System.out.println();
        return headlines.getArticles();
    }
    public Iterable<Article> findArticlesByCategory(AppUser appUser){
        ArrayList<Article> personalArticles = new ArrayList<>();
        for (Category category:appUser.getCategoryList()) {
            String catName=category.getName();
            RestTemplate restTemplate = new RestTemplate();
            Headlines headlines=restTemplate.getForObject("https://newsapi.org/v2/everything?q="+catName.toLowerCase()+"&apiKey=1f6e68f888ff45538990a57fa0e356b0",
                    Headlines.class);
            for (Article article:headlines.getArticles()) {
                personalArticles.add(article);

            }
        }
        return personalArticles;
    }
}
