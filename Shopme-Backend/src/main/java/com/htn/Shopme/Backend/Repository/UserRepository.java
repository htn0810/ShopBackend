package com.htn.Shopme.Backend.Repository;

import com.htn.Shopme.Backend.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEnabledIsTrue();
    Page<User> findAllByEnabledIsTrue(Pageable pageable);
    Optional<User> findByEmail(String email);
}
