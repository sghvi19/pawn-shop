package com.example.Controller;

import com.example.entities.Payment;
import com.example.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("payments")
@Slf4j
public class PaymentController {
    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found payments", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Payment> getAllPayment(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                           @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.debug("get all payments with page:{} and pageSize:{}", page, pageSize);
        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found payment by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(mediaType = "application/json")})})
    public Payment getByIdPayment(@Parameter(description = "payment id") @PathVariable final long id) {
        log.debug("get payment with required id:{}", id);
        return service.getById(id);
    }

    @GetMapping("/end/{itemId}")
    @Operation(summary = "Get total month payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found total month payment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Item not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public BigDecimal getTotalMonthPayment(@Parameter(description = "Item id") @PathVariable(name = "itemId") final long itemId,
                                           @Parameter(description = "Start date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startDate,
                                           @Parameter(description = "End date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate) {
        log.debug("get total month payment with required id:{} and startDate:{} and endDate:{}", itemId,startDate,endDate);
        return service.getTotalMonthPayment(itemId, startDate, endDate);
    }


    @PutMapping("{id}")
    @Operation(summary = "update payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "payment updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Payment updatePayment(@Parameter(description = "payment id") @PathVariable final long id,
                                 @Parameter(description = "payment body") @RequestBody @Valid final Payment payment) {
        log.debug("update payment with required id:{} and branch:{}", id, payment);
        return service.update(id, payment);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created payment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Payment createPayment(@Parameter(description = "payment body") @RequestBody @Valid final Payment payment) {
        log.debug("creat payment with required branch:{}", payment);
        return service.create(payment);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed payment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(mediaType = "application/json")})})
    public Payment removePayment(@Parameter(description = "payment id") @PathVariable final long id) {
        log.debug("remove payment with required id:{}", id);
        return service.remove(id);
    }
}
