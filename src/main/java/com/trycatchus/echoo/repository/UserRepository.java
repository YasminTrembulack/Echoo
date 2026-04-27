package com.trycatchus.echoo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trycatchus.echoo.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("""
    SELECT u FROM User u
    WHERE u.email = :email
       OR u.cpf = :cpf
       OR u.username = :username
    """)
    List<User> findConflictingUsers(String email, String cpf, String username);
}
