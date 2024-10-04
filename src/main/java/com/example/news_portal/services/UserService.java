package com.example.news_portal.services;

import com.example.news_portal.model.Role;
import com.example.news_portal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    User save(User user, Role role);

    User update(User user);

    void deleteById(Long id);

    User findByUsername(String username);


}
