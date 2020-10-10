package com.hoaxify.repositories;

import com.hoaxify.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> { // spring creates proxy implementation based on this interface

         User findByUsername(String username);

         Page<User> findByUsernameNot(String username, Pageable pageable);

}
