package com.pacs.scanviewer.SCV.User.controller;

import com.pacs.scanviewer.SCV.User.domain.User;
import com.pacs.scanviewer.SCV.User.domain.UserDto;
import com.pacs.scanviewer.SCV.User.service.LogOnUserService;
import com.pacs.scanviewer.SCV.User.service.MyUserDetailsService;
import com.pacs.scanviewer.SCV.User.service.UserService;
import com.pacs.scanviewer.Util.CookieUtil;
import com.pacs.scanviewer.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final LogOnUserService logOnUserService;


    //로그인 페이지로 이동
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    //회원가입 페이지로 이동
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    //마이페이지로 이동
    @GetMapping("/mypage")
    public ModelAndView mypage(HttpServletRequest request) {
        User user = null;
        String token = CookieUtil.getCookieValue(request, "jwt");
        String userCode = jwtUtil.extractUsername(token);
        Optional<User> OptionalUser = userService.findUser(userCode);
        if (OptionalUser.isPresent()) {
            user = OptionalUser.get();
        }
        ModelAndView modelAndView = new ModelAndView("user/mypage");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    //그룹 관리 페이지로 이동
    @GetMapping("/manage")
    public ModelAndView manage() {
        ModelAndView modelAndView = new ModelAndView("user/manage");
        List<User> userList = userService.findAllUser();
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

    //회원정보 설정페이지로 이동
    @GetMapping("/edit/{userCode}")
    public ModelAndView editUser(@PathVariable String userCode) {
        ModelAndView modelAndView = new ModelAndView("user/editUser");
        Optional<User> user = userService.findUser(userCode);
        user.ifPresent(value -> modelAndView.addObject("user", value));
        return modelAndView;
    }

    //업데이트 후 그룹관리 페이지로 이동
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

    //회원 탈퇴후 로그인 페이지로 이동
    @PostMapping("/delete")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.getCookieValue(request, "jwt");
        String userCode = jwtUtil.extractUsername(token);
        Optional<User> optionalUser = userService.findUser(userCode);
        User user = optionalUser.get();
        userService.deleteUser(user);
        // JWT 쿠키 삭제
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 만료 시간 설정 (0은 즉시 삭제)
        response.addCookie(cookie);
        return "redirect:/user/login";
    }

    //로그아웃 처리 후 로그인 페이지로 이동
    @GetMapping("/logout")
    public String logout(HttpServletRequest request ,HttpServletResponse response) {
        String token = CookieUtil.getCookieValue(request, "jwt");
        String userCode = jwtUtil.extractUsername(token);

        if (userCode != null) {
            logOnUserService.removeUser(userCode);
        }
        // JWT 쿠키 삭제
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 만료 시간 설정 (0은 즉시 삭제)
        response.addCookie(cookie);

        return "user/login";
    }

    //회원가입 처리 후 로그인 페이지로 이동
    @PostMapping("/joinProcess")
    public ModelAndView joinprocess(@ModelAttribute UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
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


}
