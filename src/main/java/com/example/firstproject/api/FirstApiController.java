package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // RestAPI용 컨트롤러! JSON을 반환(데이터를 반환한다). 일반 Controller는 뷰 템플릿페이지를 반환함
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!";
    }
}
