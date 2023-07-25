package com.example.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_generator")
    @SequenceGenerator(name = "payment_id_generator", sequenceName = "payment_id_gen")
    @EqualsAndHashCode.Include
    @Column(name = "payment_id")
    private long paymentId;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_value",nullable = false)
    @NotNull
    private BigDecimal paymentValue;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;

}
