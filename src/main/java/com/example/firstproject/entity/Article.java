package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity  // DB가 해당 객체를 인식가능
@AllArgsConstructor
@ToString
public class Article {

    @Id // 대표값을 지정한다(주민번호 같은것.) 제목가 내용이 동일할때 구분하기 위함
    @GeneratedValue  // 자동생성해주는 어노테이션. 1, 2, 3, ...
    private Long id;

   @Column  // DB에서 관리하는 table에 연결되게 해준다
    private String title;

    @Column
    private String content;
}
