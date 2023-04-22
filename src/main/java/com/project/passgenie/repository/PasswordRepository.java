package com.project.passgenie.repository;

import com.project.passgenie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<User,Long> {
}
