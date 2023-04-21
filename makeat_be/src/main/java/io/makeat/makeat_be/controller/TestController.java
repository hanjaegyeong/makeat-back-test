package io.makeat.makeat_be.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    //테스트용
    @GetMapping("/test/do")
    public String loginPage()
    {
        return "login";
    }

    @GetMapping("/test/token")
    public String tokenPage()
    {
        return "token";
    }
}
