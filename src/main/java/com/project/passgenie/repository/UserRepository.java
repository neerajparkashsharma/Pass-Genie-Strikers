package com.project.passgenie.repository;

import com.project.passgenie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUserName(String username);
    boolean existsByEmailAddress(String email);
    User findByUserName(String username);
    User findByEmailAddress(String email);

    boolean existsByUserNameAndPassword(String username, String password);

    boolean existsByEmailAddressAndPassword(String email, String password);
}

