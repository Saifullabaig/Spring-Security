package com.example.springsecurityhelloworld.repository;

import com.example.springsecurityhelloworld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
