package net.annakat.demo.security;

import lombok.RequiredArgsConstructor;
import net.annakat.demo.exception.UnauthorizedException;
import net.annakat.demo.model.User;
import net.annakat.demo.repository.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomerPrincipal customerPrincipal = (CustomerPrincipal) authentication.getPrincipal();
        Mono<Authentication> map = userRepository.findById(customerPrincipal.getId())
                .filter(User::isEnabled)
                .switchIfEmpty(Mono.error(new UnauthorizedException("")))
                .map(user -> authentication);
        return map;
    }
}
