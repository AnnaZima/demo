package net.annakat.demo.rest;

import lombok.RequiredArgsConstructor;
import net.annakat.demo.dto.AuthRequestDto;
import net.annakat.demo.dto.AuthResponseDto;
import net.annakat.demo.dto.UserDto;
import net.annakat.demo.mapper.UserMapper;
import net.annakat.demo.model.User;
import net.annakat.demo.security.CustomerPrincipal;
import net.annakat.demo.security.SecurityService;
import net.annakat.demo.security.TokenDetails;
import net.annakat.demo.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityService service;


    @PostMapping(path = "/register", consumes = "application/json")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        User user = userMapper.map(dto);
        return userService.registerUser(user).map(userMapper::map);
    }


    @PostMapping("/login")
    public Mono<AuthResponseDto> login (@RequestBody AuthRequestDto requestDto) {

        return service.authenticate(requestDto.getUsername(), requestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(AuthResponseDto.builder().userId(tokenDetails.getId())
                                .token(tokenDetails.getToken())
                                .issueAt(tokenDetails.getCreatedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                        .build()));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomerPrincipal customerPrincipal = (CustomerPrincipal) authentication.getPrincipal();

        return userService.getById(customerPrincipal.getId());
    }
}
