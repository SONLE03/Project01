package com.clothing.userservice.dto.request;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequest {
    private String email;
    private String phoneNumber;
    private String fullName;
    private Integer role;
    private Date dateOfBirth;
    private String password;
}
