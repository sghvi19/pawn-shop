package com.example.Controller;


import com.example.entities.Car;
import com.example.service.CarService;
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
@RequestMapping("cars")
@Slf4j
public class CarController {
    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found cars", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Car> getAllCar(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page")final int page,
                                   @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size")final int pageSize) {
        log.debug("get all cars with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page,pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found car by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "car not found", content = {@Content(mediaType = "application/json")})})
    public Car getByIdCar(@Parameter(description = "car id") @PathVariable final long id) {
        log.debug("get car with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "car updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Car updateCar(@Parameter(description = "car id")@PathVariable final long id,
                          @Parameter(description = "car body")@RequestBody @Valid final Car car) {
        log.debug("update car with required id:{} and branch:{}",id,car);
        return service.update(id, car);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created car", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Car createCar(@Parameter(description = "car body")@RequestBody @Valid final Car car) {
        log.debug("creat car with required branch:{}",car);
        return service.create(car);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed car", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "car not found", content = {@Content(mediaType = "application/json")})})
    public Car removeCar(@Parameter(description = "car id")@PathVariable final long id) {
        log.debug("remove car with required id:{}",id);
        return service.remove(id);
    }
}
