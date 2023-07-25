package com.example.Controller;

import com.example.entities.Jewelry;
import com.example.service.JewelryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("jewelries")
@Slf4j
public class JewelryController {
    private final JewelryService service;

    @Autowired
    public JewelryController(JewelryService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get jewelries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found jewelries", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Jewelry> getAllJewelry(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page")final int page,
                                           @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size")final int pageSize) {
        log.debug("get all jewelries with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page,pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get jewelry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found jewelry by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "jewelry not found", content = {@Content(mediaType = "application/json")})})
    public Jewelry getByIdJewelry(@Parameter(description = "jewelry id") @PathVariable final long id) {
        log.debug("get jewelry with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update jewelry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "jewelry updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Jewelry updateJewelry(@Parameter(description = "jewelry id")@PathVariable final long id,
                           @Parameter(description = "jewelry body")@RequestBody @Valid final Jewelry jewelry) {
        log.debug("update jewelry with required id:{} and branch:{}",id,jewelry);
        return service.update(id, jewelry);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create jewelry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created jewelry", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Jewelry createJewelry(@Parameter(description = "jewelry body")@RequestBody @Valid final Jewelry jewelry) {
        log.debug("creat jewelry with required branch:{}",jewelry);
        return service.create(jewelry);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove jewelry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed jewelry", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "jewelry not found", content = {@Content(mediaType = "application/json")})})
    public Jewelry removeJewelry(@Parameter(description = "jewelry id")@PathVariable final long id) {
        log.debug("remove jewelry with required id:{}",id);
        return service.remove(id);
    }
}
