package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.hibernate.dialect.function.AnsiTrimEmulationFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // 해당 클래스는 스프링부트와 연동되어 테스팅된다
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 예상(expected)
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        // 실제
        List<Article> articles = articleService.index();

        // 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    @DisplayName("존재하는 id 입력")
    void show_성공() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가","1111");

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("존재하지 않는 id 입력")
    void show_실패(){
        // 예상
        Long id = -1L;
        Article expected = null;  // show메소드에서 없는경우 null을 반환하도록 설계해뒀음.

        // 실제
        Article article = articleService.show(id);

        // 비교 - null은 toString()메소드 호출 X
        assertEquals(expected, article);

    }

    @Test
    @DisplayName("title과 content만 있는 dto입력")
    @Transactional  // 테스트 종료후 변경된 데이터를 롤백처리(처음으로 되돌린다)
    void create_성공() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }


    @Test
    @DisplayName("id가 포함된 dto가 입력된 경우")
    @Transactional
    void create_실패(){
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @DisplayName("존재하는 id와 title, content가 있는 dto입력")
    @Transactional
    void update_성공1(){
        // 준비
        Long id = 1L;
        String title="가나다라";
        String content="1234";
        ArticleForm dto = new ArticleForm(id, title, content);

        // 예상
        Article expected = new Article(id, title, content);


        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("존재하는 id와 title만 있는 dto입력")
    @Transactional
    void update_성공2(){
        // 준비
        Long id = 1L;
        String title="가나다라";
        String content=null;
        ArticleForm dto = new ArticleForm(id, title, content);

        // 예상
        Article expected = new Article(id, title, "1111");

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }


    @Test
    @DisplayName("존재하지 않는 id의 dto입력")
    void update_실패(){
        // 준비
        Long id = -1L;
        String title=null;
        String content=null;
        ArticleForm dto = new ArticleForm(id, title, content);

        // 예상
        Article expected = null;

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @DisplayName("존재하는 id입력")
    @Transactional
    void delete_성공(){
        // 예상
        Article expected = articleService.show(1L);

        // 실제
        Article article = articleService.delete(1L); // 삭제된 article반환

        // 비교
        assertEquals(expected, article);
    }


    @Test
    @DisplayName("존재하지 않는 id입력")
    @Transactional
    void delete_실패(){
        // 예상
        Article expected = null;

        // 실제
        Article article = articleService.delete(-1L);

        // 비교
        assertEquals(expected, article);
    }



}