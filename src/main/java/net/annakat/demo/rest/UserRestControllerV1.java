package net.annakat.demo.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.annakat.demo.dto.UserDto;
import net.annakat.demo.mapper.UserMapper;
import net.annakat.demo.model.User;
import net.annakat.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;
    private  final UserMapper userMapper;


    @PostMapping(path = "/register", consumes = "application/json")
    public Mono<UserDto> createUser(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }


    @GetMapping()
    public Flux<UserDto> getAllUser() {
        return userService.getAll().map(userMapper::map);
    }

    @PostMapping()
    public Mono<UserDto> updateUser(@RequestBody UserDto dto) {
        User user = userMapper.map(dto);
        return userService.updateUser(user).map(userMapper::map);
    }

    @PostMapping("/{id}")
    public Mono<Boolean> deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }


}
