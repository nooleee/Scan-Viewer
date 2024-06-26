package com.pacs.scanviewer.SCV.controller;

import com.pacs.scanviewer.SCV.domain.User;
import com.pacs.scanviewer.SCV.domain.UserDto;
import com.pacs.scanviewer.SCV.service.UserService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/loginProcess")
    public ModelAndView loginprocess(@ModelAttribute UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView();

        System.out.println("로그인 로직 진입 ");
        System.out.println("userCode: " + userDto.getUserCode());
        System.out.println("password: " + userDto.getPassword());
        if (userService.login(userDto)){
            modelAndView.setViewName("redirect:/user/index");
            System.out.println("로그인 성공");
        }else {
            modelAndView.setViewName("redirect:/user/login");
            System.out.println("로그인 실패");
        }

        return modelAndView;
    }

    @PostMapping("/joinProcess")
    public ModelAndView joinprocess(@ModelAttribute UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView();

        System.out.println("가입 로직 진입 ");
        System.out.println("userCode: " + userDto.getUserCode());
        System.out.println("password: " + userDto.getPassword());
        System.out.println("name: " + userDto.getName());
        System.out.println("phone: " + userDto.getPhone());
        System.out.println("group: " + userDto.getGroup());
        boolean saveComplete = userService.createUser(userDto);

        if (saveComplete){
            System.out.println("가입 성공");
            modelAndView.setViewName("redirect:/user/login");
        }else {
            System.out.println("가입 실패");
            modelAndView.setViewName("redirect:/user/index");
        }

        return modelAndView;
    }


}
