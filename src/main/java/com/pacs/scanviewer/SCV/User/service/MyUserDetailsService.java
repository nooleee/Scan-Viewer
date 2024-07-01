package com.pacs.scanviewer.SCV.User.service;

import com.pacs.scanviewer.SCV.User.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        return userRepository.findById(userCode).orElseThrow(() ->
                new UsernameNotFoundException("User not found with userCode: " + userCode));
    }
}
