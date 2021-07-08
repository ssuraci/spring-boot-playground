package it.sebastianosuraci.springboot.demo.dto;

import java.time.LocalDateTime;

import it.sebastianosuraci.springboot.core.dto.BaseSerialDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDTO extends BaseSerialDTO {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected LocalDateTime lastLogin;

}
