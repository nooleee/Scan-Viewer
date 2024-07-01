package com.pacs.scanviewer.SCV.User.controller;

import com.pacs.scanviewer.SCV.User.domain.User;
import com.pacs.scanviewer.SCV.User.domain.UserDto;
import com.pacs.scanviewer.SCV.User.service.MyUserDetailsService;
import com.pacs.scanviewer.SCV.User.service.UserService;
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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

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
    public ResponseEntity<?> loginprocess(@RequestBody UserDto userDto) {
        System.out.println("userCode: " + userDto.getUserCode());
        System.out.println("password: " + userDto.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserCode(), userDto.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userDto.getUserCode());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

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


    @GetMapping("/checkUserCode")
    public ResponseEntity<Boolean> checkUserCode(@RequestParam String userCode) {
        System.out.println("userCode: " + userCode);
        boolean isDuplicate = userService.isUserCodeDuplicate(userCode);
        System.out.println("isduplicate: " + isDuplicate);
        return ResponseEntity.ok(isDuplicate);
    }

    private static class AuthenticationResponse {
        private final String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }
    }


}
