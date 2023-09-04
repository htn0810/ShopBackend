package com.htn.Shopme.Backend.Repository;

import com.htn.Shopme.Backend.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEnabledIsTrue();

    Page<User> findAllByEnabledIsTrue(Pageable pageable);

    @Query(value = "SELECT * FROM Users WHERE CONCAT(email, ' ' , first_name, ' ', last_name) LIKE %:keyword%",
            nativeQuery = true)
    Page<User> findAllByEnabledIsTrue(Pageable pageable, @Param("keyword") String keyword);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
