package com.example.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cars")
public class Car extends Item {

    @NotBlank
    @Length(max = 128)
    @Column(name = "mark_model", length = 128, nullable = false)
    private String markModel;

    @Past
    @NotNull
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "odometer", nullable = false)
    @Min(0)
    private int odometer;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "odometer_unit", nullable = false)
    private OdometerUnitType type;

}
