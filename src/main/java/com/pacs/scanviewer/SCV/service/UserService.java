package com.pacs.scanviewer.SCV.service;

import com.pacs.scanviewer.SCV.domain.User;
import com.pacs.scanviewer.SCV.domain.UserDto;
import com.pacs.scanviewer.SCV.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean login(UserDto userDto) {
        return userRepository.findByUserCodeAndPassword(userDto.getUserCode(),userDto.getPassword()).isPresent();
    }

    public boolean createUser(UserDto userDto) {
        User user = new User(userDto);
        User save = userRepository.save(user);
        return save != null;
    }
}
