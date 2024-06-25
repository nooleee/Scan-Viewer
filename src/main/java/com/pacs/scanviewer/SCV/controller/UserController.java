package com.pacs.scanviewer.SCV.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/user")
@Controller
public class UserController {

    @GetMapping("")
    public String userIndex(){
        return "user/index";
    }
}
