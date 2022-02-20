package com.example.firstproject.dto;

// 폼 데이터를 받아올 그릇과 같은 역할

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor  // 생성자
@ToString  // toString()
@NoArgsConstructor
@Setter
public class ArticleForm {

    private Long id;  // id필드 추가
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id, title, content);
        // dto에 담긴 title과 content를 그대로 넣어서 Article(entity)을 만들어 반환해준다
    }
}
