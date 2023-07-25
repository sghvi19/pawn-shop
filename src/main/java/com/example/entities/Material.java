package com.example.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Materials")
public class Material {

    @Id
    @EqualsAndHashCode.Include
    @Min(1)
    @Column(name = "material_id")
    private long materialId;

    @Length(max = 32)
    @NotBlank
    @Column(name = "name",length = 32,nullable = false)
    private String name;
}
