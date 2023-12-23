package net.annakat.demo.repository;


import net.annakat.demo.model.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    @Modifying
    @Query("UPDATE users SET status = :DELETE WHERE id = :id")
    Mono<Boolean> deleteUser(@Param("DELETE") String status, @Param("id") Integer id);

    @Query("SELECT * FROM users WHERE user_name = :name")
    Mono<User> findByUserName(String name);
}
