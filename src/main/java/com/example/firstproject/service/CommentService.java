package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired  // db에서 comment, article데이터 모두 가져와야 하기 때문에
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId){
        // 조회 : 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 변환 : 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i=0; i < comments.size(); i++){
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
        // 반환
        //  stream 문법!(위의 for문과 동일한 역할)
        return commentRepository.findByArticleId(articleId)
                .stream()  // 하나씩 꺼내와서
                .map(comment -> CommentDto.createCommentDto(comment)) // 꺼내온 comment를 dto로 변환
                .collect(Collectors.toList());  // 리스트로 묶어준다
    }

    @Transactional  // db에 변경이 일어날 수 있기 때문에 transactinal 처리를 해줘야함. 문제발생시 rollback될 수 있도록
    public CommentDto create(Long articleId, CommentDto dto){
        // 게시글 조회 및 예외처리
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));  //  없다면 예외 발생

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);  // json데이터가 담긴 dto

        // 생성된 엔티티 db에 저장
        Comment created = commentRepository.save(comment);

        // dto로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

}
