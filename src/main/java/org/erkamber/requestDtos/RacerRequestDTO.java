package org.erkamber.requestDtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RacerRequestDTO {

    @NotNull(message = "First name cannot be null")
    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 20, message = "First name must be at most 20 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 20, message = "Last name must be at most 20 characters")
    private String lastName;

    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be empty")
    @Size(max = 30, message = "City must be at most 30 characters")
    private String city;

    @NotNull(message = "Age range cannot be null")
    @NotEmpty(message = "Age range cannot be empty")
    @Size(max = 10, message = "Age range must be at most 10 characters")
    private String ageRange;

    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Size(max = 30, message = "Email must be at most 30 characters")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String photo;
}
