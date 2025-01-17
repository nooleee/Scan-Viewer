package com.pacs.scanviewer.SCV.User.service;

import com.pacs.scanviewer.SCV.User.domain.User;
import com.pacs.scanviewer.SCV.User.domain.UserDto;
import com.pacs.scanviewer.SCV.User.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getUserCode());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(userDto.getPassword(), user.getPassword()); // 비밀번호 검증
        }
        return false;
    }

    public boolean createUser(UserDto userDto) {
        User user = new User(userDto);
        User save = userRepository.save(user);
        return save != null;
    }

    public boolean isUserCodeDuplicate(String userCode) {
        return userRepository.existsByUserCode(userCode);
    }

    public Optional<User> findUser(String userCode) {
        return userRepository.findById(userCode);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public void updateUserGroup(User user) {
        userRepository.save(user);
    }


    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
