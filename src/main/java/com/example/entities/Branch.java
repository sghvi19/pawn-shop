package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Branches")
public class Branch {

    @Id
    @Min(1)
    @EqualsAndHashCode.Include
    @Column(name = "branch_id")
    private long branchId;


    @Length(max = 128)
    @NotBlank
    @Column(name = "address",length = 128,nullable = false)
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private Set<Item> items;


}
