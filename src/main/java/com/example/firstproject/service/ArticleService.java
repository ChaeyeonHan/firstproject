package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service  // 해당 클래스를 서비스로 인식하여 스프링 부트에 객체를 생성(등록)
public class ArticleService {

    @Autowired // DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto){
        Article article = dto.toEntity();  // dto를 entity로 변환

        // 기존 데이터를 수정하도록 하는 것을 방지하기 위해 if문 추가(post는 생성만!)
        if (article.getId() != null){  // id가 만약 존재한다면, null반환(id는 db에서 자동생성되므로)
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto){
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if (target == null || id != article.getId()){
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id){
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null){
            return null;
        }

        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다
    public List<Article> createArticles(List<ArticleForm> dtos){
        // dto묶음을 entity묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())  // dto가 매번 올때마다 entity로 변환
                .collect(Collectors.toList());  // List로 변환해준다


        // entity묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

//        //위 코드를 for문으로
//        for (int i=0; i < articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 강제 예외 발생시키기
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        // 결과값 반환
        return articleList;
    }
}
