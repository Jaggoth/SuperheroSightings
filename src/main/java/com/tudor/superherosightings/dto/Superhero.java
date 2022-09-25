package com.tudor.superherosightings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Superhero {
    private int id;
    @NotBlank(message = "Name can not be empty")
    @Size(max = 30, message = "Name can not exceed 30 characters")
    private String name;
    @Size(max = 250, message = "Description can not exceed 250 characters")
    private String description;
    @NotBlank(message = "Power can not be empty")
    @Size(max = 50, message = "Power can not exceed 50 characters")
    private String power;
}
