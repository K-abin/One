package com.spring.vhrserve.controller;

import com.spring.vhrserve.bean.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author CXB
 * @ClassName LoginController
 * @date 2022/9/18 14:29
 * @Description TODO
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("没有登录，请登录");
    }
}
