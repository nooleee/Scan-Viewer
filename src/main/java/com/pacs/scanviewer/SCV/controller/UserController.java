package com.pacs.scanviewer.SCV.controller;

import com.pacs.scanviewer.SCV.domain.User;
import com.pacs.scanviewer.SCV.domain.UserDto;
import com.pacs.scanviewer.SCV.service.UserService;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


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

    @GetMapping("/mypage")
    public String mypage() {
        return "user/mypage";
    }

    @GetMapping("/manage")
    public ModelAndView manage() {
        ModelAndView modelAndView = new ModelAndView("user/manage");
        List<User> userList = userService.findAllUser();
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

    @GetMapping("/edit/{userCode}")
    public ModelAndView editUser(@PathVariable String userCode) {
        ModelAndView modelAndView = new ModelAndView("user/editUser");
        Optional<User> user = userService.findUser(userCode);
        user.ifPresent(value -> modelAndView.addObject("user", value));
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserDto userDto) {
        Optional<User> optionalUser = userService.findUser(userDto.getUserCode());
        User user = optionalUser.get();
        System.out.println("[60]userCode: " + user.getUserCode());
        UserDto userDto1 = new UserDto(user);
        userDto1.setGroup(userDto.getGroup());
        user = new User(userDto1);
        System.out.println("[64]userCode: " + user.getUserCode());

        userService.updateUserGroup(user);
        return "redirect:/user/manage";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userCode, HttpSession session) {
        Optional<User> optionalUser = userService.findUser(userCode);
        User user = optionalUser.get();
        userService.deleteUser(user);
        session.invalidate(); // 로그아웃 처리
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "user/login";
    }


    @PostMapping("/loginProcess")
    public ModelAndView loginprocess(@ModelAttribute UserDto userDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        System.out.println("로그인 로직 진입 ");
        System.out.println("userCode: " + userDto.getUserCode());
        System.out.println("password: " + userDto.getPassword());
        if (userService.login(userDto)) {
            Optional<User> userOptional = userService.findUser(userDto.getUserCode());
            User user = userOptional.get();
            session.setAttribute("user", user);
            modelAndView.setViewName("redirect:/user/mypage");
            System.out.println("로그인 성공");
        } else {
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

        if (saveComplete) {
            System.out.println("가입 성공");
            modelAndView.setViewName("redirect:/user/login");
        } else {
            System.out.println("가입 실패");
            modelAndView.setViewName("redirect:/user/index");
        }

        return modelAndView;
    }


    @GetMapping("/checkUserCode")
    public ResponseEntity<Boolean> checkUserCode(@RequestParam String userCode) {
        System.out.println("userCode: " + userCode);
        boolean isDuplicate = userService.isUserCodeDuplicate(userCode);
        System.out.println("isduplicate: " + isDuplicate);
        return ResponseEntity.ok(isDuplicate);
    }


}
