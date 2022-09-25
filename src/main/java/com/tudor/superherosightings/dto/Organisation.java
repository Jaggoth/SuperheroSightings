package com.tudor.superherosightings.dto;

import java.util.List;

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
public class Organisation {
    private int id;
    @NotBlank(message = "Name can not be empty")
    @Size(max = 30, message = "Name can not exceed 30 characters")
    private String name;
    @NotBlank(message = "Address can not be empty")
    @Size(max = 250, message = "Address can not exceed 250 characters")
    private String address;
    @Size(max = 250, message = "Description can not exceed 250 characters")
    private String description;
    private List<Superhero> members;
}
