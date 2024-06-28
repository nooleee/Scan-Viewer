package com.pacs.scanviewer.SCV.service;

import com.pacs.scanviewer.SCV.domain.User;
import com.pacs.scanviewer.SCV.domain.UserDto;
import com.pacs.scanviewer.SCV.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean login(UserDto userDto) {
        return userRepository.findByUserCodeAndPassword(userDto.getUserCode(), userDto.getPassword()).isPresent();
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
