package net.annakat.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class BearerTokenServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtHandler jwtHandler;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Function<String, Mono<String>> getBearerValue = authValue -> Mono
            .justOrEmpty(authValue.substring(BEARER_PREFIX.length()));

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        Mono<Authentication> authenticationMono = extractHeader(exchange)
                .flatMap(getBearerValue)
                .flatMap(jwtHandler::checkToken)
                .flatMap(UserAuthenticationBearer::create);
        return authenticationMono;
    }

    private Mono<String> extractHeader(ServerWebExchange exchan) {
        return Mono.justOrEmpty(exchan.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }
}
