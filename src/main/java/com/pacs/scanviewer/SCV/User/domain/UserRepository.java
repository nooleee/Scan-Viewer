package com.pacs.scanviewer.SCV.User.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByUserCodeAndPassword(String userCode, String password);
    public boolean existsByUserCode(String userCode);
}
