package com.example.firstproject.dto;

// 폼 데이터를 받아올 그릇과 같은 역할

import com.example.firstproject.entity.Article;

public class ArticleForm {

    private String title;
    private String content;

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Article toEntity() {
        return new Article(null, title, content);
        // dto에 담긴 title과 content를 그대로 넣어서 Article(entity)을 만들어 반환해준다
    }
}
