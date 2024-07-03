package com.pacs.scanviewer.SCV.User.service;

import com.pacs.scanviewer.SCV.User.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private List<String> loggedInUsers = new CopyOnWriteArrayList<>();

    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        return userRepository.findById(userCode).orElseThrow(() ->
                new UsernameNotFoundException("User not found with userCode: " + userCode));
    }

    public List<String> getLoggedInUsers() {
        return loggedInUsers;
    }

    public void removeLoggedInUser(String userCode) {
        loggedInUsers.remove(userCode);
    }
}
