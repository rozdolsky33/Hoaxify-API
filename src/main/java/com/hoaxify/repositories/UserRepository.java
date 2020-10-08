package com.hoaxify.repositories;

import com.hoaxify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> { // spring creates proxy implementation based on this interface

         User findByUsername(String username);

}
