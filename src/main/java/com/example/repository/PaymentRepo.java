package com.example.repository;

import com.example.entities.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;


@Repository
public interface PaymentRepo extends PagingAndSortingRepository<Payment,Long> {
    @Query("SELECT SUM(p.paymentValue) " +
            "FROM Payment p WHERE p.item.itemId = :itemId AND p.paymentDate " +
            "BETWEEN :startDate AND :endDate")
    BigDecimal totalMonthPayment(long itemId, LocalDate startDate, LocalDate endDate);


}