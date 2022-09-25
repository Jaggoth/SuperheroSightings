package com.tudor.superherosightings.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Sighting {
    private int id;
    private Superhero superhero;
    private Location location;
    @NotBlank(message = "Date must not be empty")
    private LocalDate date;
}
