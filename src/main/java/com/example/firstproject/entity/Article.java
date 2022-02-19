package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity  // DB가 해당 객체를 인식가능 + (해당 클래스로 table을 만든다)
@AllArgsConstructor
@NoArgsConstructor  // 디폴트 생성자 추가!
@ToString
@Getter  // getId()를 사용하기위해 추가
public class Article {

    @Id // 대표값을 지정한다(주민등록번호 같은것.) 제목가 내용이 동일할때 구분하기 위함
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // DB가 id를 자동생성하는 어노테이션
    private Long id;

   @Column  // DB에서 관리하는 table에 연결되게 해준다
    private String title;

    @Column
    private String content;

//    Article(){  // 디폴트 생성자
//
//    }


}
