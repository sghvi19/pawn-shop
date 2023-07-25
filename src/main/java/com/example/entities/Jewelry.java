package com.example.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Jewelries")
public class Jewelry extends Item{

    @NotBlank
    @Length(max = 128)
    @Column(name = "description",length = 128,nullable = false)
    private String desription;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "jewelry_material_map",
            joinColumns = @JoinColumn(name = "jewelry_id", referencedColumnName = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    )
    private Set<Material> materials;
}
