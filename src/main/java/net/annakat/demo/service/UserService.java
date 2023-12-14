package net.annakat.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.annakat.demo.dto.UserDto;
import net.annakat.demo.mapper.UserMapper;
import net.annakat.demo.model.Status;
import net.annakat.demo.model.User;
import net.annakat.demo.model.UserRole;
import net.annakat.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    public Mono<UserDto> getById(Integer id) {
        return userRepository.findById(id).map(userMapper::map);
    }


    public Mono<User> updateUser(User user) {
      return userRepository.findById(user.getId()).flatMap(user1 -> {
          user.setPassword(user1.getPassword());
          user.setUserRole(user1.getUserRole());
          user.setStatus(user1.getStatus());
          return userRepository.save(user);
      });
    }

    public Mono<Boolean> deleteUser(Integer id) {
        return userRepository.deleteUser("delete", id);
    }

    public Mono<User> getByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    public Mono<UserDto> create(UserDto dto) {
        User user = userMapper.map(dto);
        user.setStatus(Status.ACTIVE);
        user.setUserRole(UserRole.ROLE_USER);
        return userRepository.save(user).map(u -> {
            u.setEnabled(true);
            return userMapper.map(u);
        });
    }

    public Mono<User> registerUser(User user) {
        return userRepository.save(user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .userRole(UserRole.ROLE_USER)
                .enabled(true)
                .status(Status.ACTIVE)
                .build()
        ).doOnSuccess(u -> {
            log.info("registerUser user^ {} created", u);
        });
    }

}
