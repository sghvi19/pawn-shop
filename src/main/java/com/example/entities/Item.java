package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "item_id_generator")
    @SequenceGenerator(name = "item_id_generator",sequenceName = "item_id_gen")
    @EqualsAndHashCode.Include
    private long itemId;

    @Past
    @NotNull
    @Column(name = "pawn_date",nullable = false)
    private LocalDate pawnDate;

    @NotNull
    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "month_fee",nullable = false)
    private BigDecimal monthFee;

    @Column(name = "is_with_owner",nullable = false)
    private boolean isWithOwner;

    @Column(name = "take_out_date")
    private LocalDate takeOutDate;

    @Column(name = "confiscated",nullable = false)
    private boolean isConfiscated;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<Payment> payment;

}
