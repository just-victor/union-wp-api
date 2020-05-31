package chk.union.wp.dto;

import chk.union.wp.enums.CableType;
import lombok.Data;

import java.time.Instant;

@Data
public class EventDto {
    private Long id;
    private Long userId;
    private String userName;
    private Instant dateStart;
    private Instant dateEnd;
    private String comment;
    private String telephone;
    private CableType cable;
    private Boolean approved;
}
