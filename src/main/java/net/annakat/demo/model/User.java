package net.annakat.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table ("users")
public class User {

    @Id
    private Integer id;
    @Column("user_name")
    private String userName;
    private String password;
    @Column("user_role")
    private UserRole userRole;
    @Transient
    private List<Event> events;
    private Status status;
    @Transient
    private boolean enabled;



}
