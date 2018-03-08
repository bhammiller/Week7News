package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Headlines {

    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

    public Headlines() {
        this.articles=new ArrayList<>();
    }


    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "Headlines{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", article=" + articles +
                '}';
    }
}
