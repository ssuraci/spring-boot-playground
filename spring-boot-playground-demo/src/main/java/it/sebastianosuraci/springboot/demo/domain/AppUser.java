package it.sebastianosuraci.springboot.demo.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import it.sebastianosuraci.springboot.core.domain.BaseEntitySerial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser extends BaseEntitySerial {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected LocalDateTime lastLogin;
    protected String passwd; 
}
