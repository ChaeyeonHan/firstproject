package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";   // templates안에 articles/new.mustache 파일을 찾아 브라우저에 전송
    }

    // new.mustache에서 method="post"로 던졌기에 post로 받아준다.
    // @PostMapping("어디로받을지")
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){  // 폼데이터가 던져져서 온다.

        System.out.println(form.toString()); // toString으로 찍어보기
        return "";
    }

}
