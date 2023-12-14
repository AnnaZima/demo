package net.annakat.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("events")
public class Event {
    @Id
    private Integer id;
    @Column("event_name")
    private String eventName;
    @Column("user_id")
    private Integer userId;
    @Column("file_id")
    private Integer fileId;
    @Transient
    private User user;
    @Transient
    private FileEntity file;
    @Column("status")
    private Status status;
}
