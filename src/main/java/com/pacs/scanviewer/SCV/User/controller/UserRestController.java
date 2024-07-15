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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final LogOnUserService logOnUserService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;

    //중복 아이디인지 검증하는 api
    @GetMapping("/checkUserCode")
    public ResponseEntity<Boolean> checkUserCode(@RequestParam String userCode) {
        boolean isDuplicate = userService.isUserCodeDuplicate(userCode);
        return ResponseEntity.ok(isDuplicate);
    }

    //현재 로그인중인 유저 정보를 반환하는 api
    @GetMapping("/userInfo")
    public ResponseEntity<User> getUserInfo(HttpServletRequest request) {
        String token = CookieUtil.getCookieValue(request, "jwt");
        String userCode = jwtUtil.extractUsername(token);
        Optional<User> user = userService.findUser(userCode);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //전체 유저의 아이디를 반환하는 api
    @GetMapping("/allUsers")
    public List<String> getAllUsers() {
        List<User> allUsers = userService.findAllUser();
        List<String> userCodes = new ArrayList<>();
        for (User user : allUsers) {
            userCodes.add(user.getUserCode());
        }
        userCodes.forEach(System.out::println);
        return userCodes;
    }

    //로그인 성공 여부와 함께 토큰을 발행해주는 api
    @PostMapping("/loginProcess")
    public ResponseEntity<?> loginprocess(@RequestBody UserDto userDto) {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserCode(), userDto.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userDto.getUserCode());
        final String jwt = jwtUtil.generateToken(userDetails);

        logOnUserService.addUser(userDto.getUserCode());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    //현재 로그인중인  유저의 목록을 반환하는 api
    @GetMapping("/logOnUsers")
    public List<String> getLoggedInUsers() {
        // logOnUserService.getLoggedInUsers()가 Set<String>을 반환하는 경우 List<String>으로 변환하여 반환
        Set<String> loggedInUsers = logOnUserService.getLoggedInUsers();
        loggedInUsers.forEach(System.out::println);
        return new ArrayList<>(logOnUserService.getLoggedInUsers());
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
