package com.pacs.scanviewer.SCV.User.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LogOnUserService {

    private Set<String> loggedInUsers = new HashSet<>();

    public void addUser(String userCode) {
        loggedInUsers.add(userCode);
    }

    public void removeUser(String userCode) {
        loggedInUsers.remove(userCode);
    }

    public Set<String> getLoggedInUsers() {
        return loggedInUsers;
    }
}
