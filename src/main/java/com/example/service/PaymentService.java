package com.example.service;

import com.example.entities.Item;
import com.example.entities.Payment;
import com.example.exception.ItemNotFoundException;
import com.example.exception.PaymentNotFoundException;
import com.example.repository.ItemRepo;
import com.example.repository.PaymentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {
    private final PaymentRepo repo;
    private final ItemRepo itemRepo;

    @Autowired
    public PaymentService(PaymentRepo repo, ItemRepo itemRepo) {
        this.repo = repo;
        this.itemRepo = itemRepo;
    }

    @Cacheable("payments")
    public Iterable<Payment> getAll(final int page, final int pageSize) {
        log.info("getting all payments");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Payment getById(final long id) {
        log.info("getting payment by id");
        return repo.findById(id).orElseThrow(() -> new PaymentNotFoundException(String.format("Payment with id: %d not found!", id)));
    }

    public BigDecimal getTotalMonthPayment(final long id, final LocalDate startDate, final LocalDate endDate) {
        if (!itemRepo.existsById(id)) {
            throw new ItemNotFoundException(String.format("Item with id: %d not found!", id));
        }
        return repo.totalMonthPayment(id, startDate, endDate);
    }

    @CacheEvict(value = "payments", allEntries = true)
    public Payment update(final long id, final Payment payment) {
        if (!repo.existsById(id)) {
            log.info("payment does not exist");
            throw new PaymentNotFoundException(String.format("Payment with id: %d not found!", id));
        }
        log.info("updating payment");
        payment.setPaymentId(id);
        repo.save(payment);
        return payment;
    }

    @CacheEvict(value = "payments", allEntries = true)
    public Payment create(final Payment payment) {
        log.info("creating payment");
        return repo.save(payment);
    }

    @CacheEvict(value = "payments", allEntries = true)
    public Payment remove(final long id) {
        log.info("removing payment");
        if (repo.existsById(id)) {
            Payment payment = repo.findById(id).get();
            repo.deleteById(id);
            return payment;
        }
        throw new PaymentNotFoundException(String.format("Payment with id: %d not found!", id));
    }

    @Scheduled(cron = "1 0 0 * * *")
    public void checkPayments() {
        int today = LocalDate.now().getDayOfMonth();
        List<Item> items = itemRepo.findAllItems();

        items.stream().
                filter(item -> item.getPawnDate().getDayOfMonth() == today)
                .filter(item -> getTotalMonthPayment(item.getItemId(), LocalDate.now().minusMonths(1), LocalDate.now())
                        .compareTo(item.getMonthFee()) < 0)
                .forEach(item -> {
                    item.setConfiscated(true);
                    itemRepo.save(item);
                });
    }
}
