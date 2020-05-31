package chk.union.wp.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String telephone;
    private Boolean isAdmin = false;
}
