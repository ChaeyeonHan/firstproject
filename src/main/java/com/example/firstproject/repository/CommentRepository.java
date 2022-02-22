package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    @Query(value =
            "SELECT *" +
            " FROM comment" +
            " WHERE article_id = :articleId",
            nativeQuery = true)
    List<Comment> findByArticleId(@Param("articleId") Long articleId);

    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(@Param("nickname")String nickname);


    //알파벳 i가 포함된 모든 댓글 조회
    @Query(value = "SELECT * FROM comment"
            + " WHERE BODY LIKE '%i%'",
            nativeQuery = true)
    List<Comment> findByAlpha_I();

//    // 특정 알파벳이 포함된 모든 댓글 조회
//    // 자꾸 해당 db를 찾을 수 없다는 에러발생 -> 해결못함(내가 작성한 sql문이 틀린 것으로 추청됨)
//    @Query(value = "SELECT * FROM comment WHERE BODY LIKE alpha = :alpha", nativeQuery = true)
//    List<Comment> findByAlpha(@Param("alpha")String alpha);

//    // 특정 내용으로 댓글 찾기
//    @Query(value = "SELECT *"
//            +" FROM comment WHERE BODY LIKE '%content%' = :content")  // 구문이 틀린듯(주석풀면 테스트 아예 실행X)
//    List<Comment> findByContent(@Param("content")String content);


    // 공백이 포함된 댓글 조회
    @Query(value="SELECT * FROM comment WHERE BODY LIKE '% %'", nativeQuery = true)
    List<Comment> findByInclude_Space();

    // 공백인 댓글 조회
    @Query(value="SELECT * FROM comment WHERE body = ' '", nativeQuery = true)
    List<Comment> findSpace();


}
