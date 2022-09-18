package com.spring.vhrserve.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author CXB
 * @ClassName HelloController
 * @date 2022/9/18 12:54
 * @Description TODO
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

}
