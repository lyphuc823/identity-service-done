package com.lyphuc.identity_service.dto.request;

import com.lyphuc.identity_service.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 18,message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
