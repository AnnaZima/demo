package net.annakat.demo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.annakat.demo.model.FileEntity;
import net.annakat.demo.model.Status;
import net.annakat.demo.model.User;
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventDto {

    private Integer id;
    private String eventName;
    private Integer userId;
    private Integer fileId;
    private User user;
    private FileEntity file;
    private Status status;
}
