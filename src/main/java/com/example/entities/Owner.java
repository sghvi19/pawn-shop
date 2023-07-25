package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "owner_id_generator")
    @SequenceGenerator(name = "owner_id_generator",sequenceName = "owner_id_gen")
    @EqualsAndHashCode.Include
    @Column(name = "owner_id")
    private long ownerId;

    @NotBlank
    @Column(name = "full_name",nullable = false)
    private String fullName;

    @Past
    @NotNull
    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    @Column(name = "personal_no",nullable = false,unique = true)
    @Pattern(regexp = "\\d{11}")
    private String personalNo;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Item> items;


}
