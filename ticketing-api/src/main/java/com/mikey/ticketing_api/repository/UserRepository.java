package com.mikey.ticketing_api.repository;

import com.mikey.ticketing_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
