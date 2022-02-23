package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {

    private Long id;
    @JsonProperty("article_id")  // json에서 article_id로 날라온다고 알려주기 -> 자동으로 매핑시켜준다
    private Long articleId;  // 게시글의 id
    private String nickname;
    private String body;


    public static CommentDto createCommentDto(Comment comment) {  // dto로 변경하는 메소드
        return new CommentDto(   // CommentDto를 만드는 메소드
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
