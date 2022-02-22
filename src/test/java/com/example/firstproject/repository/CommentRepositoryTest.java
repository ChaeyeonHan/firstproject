package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // JPA와 연동한 테스트
@WebAppConfiguration
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")  // 테스트 결과에 보여줄 이름
    void findByArticleId() {
        /* Case 1 : 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 출력!");
        }

        /* Case 2 : 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected, comments, "1번 글은 댓글이 없음");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 -> 9번 게시글 없다! */
        {
            // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            //Article article = articleRepository.findById(articleId).orElse(null);
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected, comments, "9번 게시글은 존재하지 않음");  // 둘 다 null
        }

        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9999L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            //Article article = articleRepository.findById(articleId).orElse(null);
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected, comments, "9999번 게시글은 없음");

        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1 : Park의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");

            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park이 작성한 모든 댓글");
        }

        /* Case 2 : Kim의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Kim";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");

            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Kim이 작성한 모든 댓글");
        }

        /* Case 3 : null의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = null;

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "null이 작성한 댓글은 없습니다");
        }
   }

    @Test
    @DisplayName("내용으로 댓글 조회")
    void findBySpace(){

        /* Case 1 : 공백이 포함된 모든 댓글 조회 */
        {
            // 실제 수행
            List<Comment> comments = commentRepository.findByInclude_Space();

            // 예상
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Choi", "쇼생크의 탈출");

            Comment d = new Comment(10L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Han", "ice cream");
            Comment e = new Comment(11L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Han", "i like games");
            Comment f = new Comment(12L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "anony", " ");

            List<Comment> expected = Arrays.asList(a, b, c, d, e, f);

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 2 : 공백인 댓글 조회 */
        {
            // 실제 수행
            List<Comment> comments = commentRepository.findSpace();

            // 예상
            Comment a = new Comment(12L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "anony", " ");
            List<Comment> expected = Arrays.asList(a);

            // 검증
            assertEquals(expected.toString(), comments.toString());

        }

        /* Case 3 : i가 들어간 모든 댓글 조회 5번, 6번 게시글에 각각 한개씩 존재 */
        {
            // 입력 데이터 준비
//            String alpha = "i";

            // 실제 수행
            List<Comment> comments = commentRepository.findByAlpha_I();

            // 예상
            Comment a = new Comment(10L, new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Han", "ice cream");
            Comment b = new Comment(11L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Han", "i like games");
            List<Comment> expected = Arrays.asList(a, b);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "알파벳 i가 포함된 모든 댓긓");
        }

        // + 특정 내용으로 게시글 찾기, 특정 알파벳으로 게시글 찾기(파라미터로 받아서하는 것) 못함

    }
}