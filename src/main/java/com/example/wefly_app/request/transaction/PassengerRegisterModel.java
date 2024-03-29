package com.example.wefly_app.request.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class PassengerRegisterModel {
    @NotEmpty(message = "first name is required")
    private String firstName;
    @NotEmpty(message = "last name is required")
    private String lastName;
    @NotEmpty(message = "passenger type is required")
    @Pattern(regexp = "ADULT|CHILD|INFANT", message = "seatClass must be one of the following: ADULT or CHILD or INFANT")
    private String passengerType;
    @NotEmpty(message = "nationality is required")
    private String nationality;
    @NotNull(message = "date of birth is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

}
