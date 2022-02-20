package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  // log를 찍기위해 추가하는 어노테이션
@RestController  // RestAPI용 컨트롤러, 데이터(JSON)를 반환한다
public class ArticleApiController {

    @Autowired  // DI(외부에서 가져온다)
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")  // 목록 조회
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")  // 개별항목 조회
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){  // @RequestBody: JSON데이터 받기. request의 body에서 받아와라.
        Article article = dto.toEntity(); // dto를 entity로 바꾼다
        return articleRepository.save(article);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                         @RequestBody ArticleForm dto){  // ResponseEntity로 담아서 보내면, 상태코드를 같이 전송할 수 있음.

        // 1. 수정용 entity 생성
        Article article = dto.toEntity();  // dto를 entity로 바꾼다
        log.info("id: {}, article: {}", id, article.toString());  // 중괄호에 값이 들어간다!

        // 2. 대상 entity 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if (target == null || id != article.getId()){  // 없는 걸 수정  url로 던져진 id와 dto의 id가 다른경우
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // HttpStatus.BAD_REQUEST가 400번임
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article); // - 수정시 일부 데이터만 수정하는 경우(content만), 다른 데이터값인 title이 null로 바뀌어 문제가 발생하기에, patch사용(target을 article에 붙여준다.)
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

   // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
