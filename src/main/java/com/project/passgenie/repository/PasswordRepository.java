package com.project.passgenie.repository;

import com.project.passgenie.entity.Password;
import com.project.passgenie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<Password,Long> {
    List<Password> findAllByUserId(Long userId);

    List<Password> findAllByUserIdAndIsActiveTrue(Long userId);

    Optional<Password> findByIdAndIsActiveTrue(Long passwordId);
}
