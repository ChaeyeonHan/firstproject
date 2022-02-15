package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {  // <관리대상 엔티티, 엔티티의 대표값 타입(id의 타입 = long)


}
