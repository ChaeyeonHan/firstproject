package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")  // URL 요청 연결
    public String niceToMeetYou(Model model){
        model.addAttribute("username","codus!");
        // addAttriubute : 변수등록
        // '홍팍'이라는 값을 username에 연결시켜서 model이 보내준다

        return "greetings"; // templates/greetings.mustache 파일을 찾아 브라우저로 전송!
        // return "페이지명";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "채연");
        return "goodbye";
    }
}
