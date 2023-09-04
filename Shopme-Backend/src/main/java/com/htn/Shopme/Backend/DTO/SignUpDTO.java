package com.htn.Shopme.Backend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
