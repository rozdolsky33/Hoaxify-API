package com.hoaxify.repositories;

import com.hoaxify.entity.Hoax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaxRepository extends JpaRepository<Hoax, Long> {
}
