package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j  // simple logging~ 약자. 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired  // 스프링부트가 미리 생성해둔 객체를 가져다 자동 연결된다!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";   // templates안에 articles/new.mustache 파일을 찾아 브라우저에 전송
    }

    // new.mustache에서 method="post"로 던졌기에 post로 받아준다.
    // @PostMapping("어디로받을지")
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){  // 폼데이터가 던져져서 온다. dto로 받아온다
//        System.out.println(form.toString()); // toString으로 찍어보기 -> 실제 이렇게하면 절대 안됨 => 로깅기능으로 대체!!(블랙박스 역할)
        log.info(form.toString());


        // 1. DTO를 Entity로 변환!
        Article article = form.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 만들기
        Article saved = articleRepository.save(article);  // 위에서 변환한 article(entity)를 save해주고, saved라는 이름으로 반환시킴.
//        System.out.println(saved.toString());
        log.info(saved.toString());
        return "";
    }

    @GetMapping("/articles/{id}")  // id위치에 들어가는 수는 변한다
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        // 1. id로 데이터를 찾는다
        Article articleEntity = articleRepository.findById(id).orElse(null);  // 해당 id값이 없으면 null을 반환해라
//        Optional<Article> articleEntity2 = articleRepository.findById(id);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);  // article이라는 이름으로 articleEntity를 등록해준다

        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

}
