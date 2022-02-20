package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
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

    @Autowired  // DI(외부에서 가져온다). 생성 객체를 가져와 연결
    private ArticleService articleService;  // repository를 통해 데이터를 가져왔는데, 이제는 service를 통해서 가져오기.


    // GET
    @GetMapping("/api/articles")  // 목록 조회
    public List<Article> index(){
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")  // 개별항목 조회
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    // POST : 생성요청
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){  // @RequestBody: JSON데이터 받기. request의 body에서 받아와라.
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH : 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                         @RequestBody ArticleForm dto){  // ResponseEntity로 담아서 보내면, 상태코드를 같이 전송할 수 있음.
        Article updated = articleService.update(id, dto);  // 무엇을 받아오고, 리턴하는지에 대한 정보만 알고 있으면 된다!
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

   // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
