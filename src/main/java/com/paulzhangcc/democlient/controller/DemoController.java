package com.paulzhangcc.democlient.controller;


import com.paulzhangcc.demo.rpc.dto.DemoDTO;
import com.paulzhangcc.democlient.security.extent.UserDetailsServiceExtend;
import com.paulzhangcc.democlient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    @Autowired
    UserDetailsServiceExtend userDetailsServiceExtend;
    @Autowired
    UserService userService;
    @RequestMapping("/get")
    public List<DemoDTO> top10() {
        return userService.top();
    }

    @RequestMapping("/")
    public String index() {
        return "加载中...";
    }

    @RequestMapping("/register")
    public boolean register(String username,String password) {
        return userDetailsServiceExtend.insert(username,password);
    }
}
