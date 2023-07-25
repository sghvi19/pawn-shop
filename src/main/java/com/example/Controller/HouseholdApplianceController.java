package com.example.Controller;

import com.example.entities.HouseholdAppliance;
import com.example.service.HouseholdApplianceService;
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
@RequestMapping("households")
@Slf4j
public class HouseholdApplianceController {
    private final HouseholdApplianceService service;

    @Autowired
    public HouseholdApplianceController(HouseholdApplianceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get households")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found households", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<HouseholdAppliance> getAllHousehold(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page")final int page,
                                                        @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size")final int pageSize) {
        log.debug("get all households with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page,pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get household")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found household by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "household not found", content = {@Content(mediaType = "application/json")})})
    public HouseholdAppliance getByIdHousehold(@Parameter(description = "household id") @PathVariable final long id) {
        log.debug("get household with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update household")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "household updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public HouseholdAppliance updateHousehold(@Parameter(description = "household id")@PathVariable final long id,
                      @Parameter(description = "household body")@RequestBody @Valid final HouseholdAppliance household) {
        log.debug("update household with required id:{} and branch:{}",id,household);
        return service.update(id, household);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create household")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created household", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public HouseholdAppliance createHousehold(@Parameter(description = "household body")@RequestBody @Valid final HouseholdAppliance household) {
        log.debug("creat household with required branch:{}",household);
        return service.create(household);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove household")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed household", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "household not found", content = {@Content(mediaType = "application/json")})})
    public HouseholdAppliance removeHousehold(@Parameter(description = "household id")@PathVariable final long id) {
        log.debug("remove household with required id:{}",id);
        return service.remove(id);
    }
}
