package com.example.news_portal.services;

import com.example.news_portal.exceptions.EntityNotFoundException;
import com.example.news_portal.exceptions.UserNameIsPresentException;
import com.example.news_portal.model.Role;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.DatabaseUserRepository;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {

    private final DatabaseUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с ID {0} не найден!", id)));
    }

    @Override
    public User save(User user, Role role) {

        if (userRepository.findUserByName(user.getUsername()).isPresent()) {
            throw new UserNameIsPresentException("Пользователь с именем " + user.getUsername() + " зарегистрирован в базе!");
        }

        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User update(User user) {

        if (userRepository.findUserByName(user.getUsername()).isPresent()) {
            throw new UserNameIsPresentException("Пользователь с именем " + user.getUsername() + " зарегистрирован в базе!");
        }

        User existedUser = findById(user.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(existedUser.getRoles());
        BeanUtils.copyNonNullProperties(user, existedUser);

        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format("User with username {0} not found!", username)
                )
        );
    }
}
