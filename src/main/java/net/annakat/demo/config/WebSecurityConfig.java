package net.annakat.demo.config;

import net.annakat.demo.security.AuthenticationManager;
import net.annakat.demo.security.BearerTokenServerAuthenticationConverter;
import net.annakat.demo.security.JwtHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity(useAuthorizationManager=true)
public class WebSecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    private final String[] publicRoutes = {"/api/v1/auth/register", "/api/v1/auth/login"};

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        ServerHttpSecurity serverHttpSecurity = http
                .csrf().disable()
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS)
                        .permitAll()
                        .pathMatchers(publicRoutes)
                        .permitAll()
                        .pathMatchers("/api/v1/users/**").permitAll()
                        .anyExchange()
                        .authenticated());
        serverHttpSecurity.exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))).and()
                .addFilterAt(webFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION);
       return serverHttpSecurity.build();
    }


    private AuthenticationWebFilter webFilter(AuthenticationManager authenticationManager) {
        AuthenticationWebFilter webFilter = new AuthenticationWebFilter(authenticationManager);
        webFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(new JwtHandler(secret)));
        webFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        return webFilter;
    }


}
