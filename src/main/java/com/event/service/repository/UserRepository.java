package com.event.service.repository;

import com.event.service.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findUserById(String id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
