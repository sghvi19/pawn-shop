package com.example.entities;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Household_appliances")
public class HouseholdAppliance extends Item {

    @NotBlank
    @Length(max = 32)
    @Column(name = "firm", nullable = false, length = 32)
    private String firm;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "technic_type", nullable = false)
    private TechnicType type;

    @Column(name = "damage_description")
    private String damageDescription;
}
