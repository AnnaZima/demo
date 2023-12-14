package net.annakat.demo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.annakat.demo.model.Status;



@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileDto {
    Integer id;
    String name;
    String location;
    Status status;
}
