package com.example.Controller;

import com.example.entities.Owner;
import com.example.service.OwnerService;
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
@RequestMapping("owners")
@Slf4j
public class OwnerController {
    private final OwnerService service;

    @Autowired
    public OwnerController(OwnerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found owners", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Owner> getAllOwner(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                       @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.debug("get all owners with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found owner by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "owner not found", content = {@Content(mediaType = "application/json")})})
    public Owner getByIdOwner(@Parameter(description = "owner id") @PathVariable final long id) {
        log.debug("get owner with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "owner updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Owner with that personNo already exists error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Owner updateOwner(@Parameter(description = "owner id") @PathVariable final long id,
                        @Parameter(description = "owner body") @Valid @RequestBody final Owner owner) {
        log.debug("update owner with required id:{} and branch:{}",id,owner);
        return service.update(id, owner);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created owner", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Owner with that personNo already exists error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Owner createOwner(@Parameter(description = "owner body") @RequestBody @Valid final Owner owner) {
        log.debug("creat owner with required branch:{}",owner);
        return service.create(owner);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed owner", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "owner not found", content = {@Content(mediaType = "application/json")})})
    public Owner removeOwner(@Parameter(description = "owner id") @PathVariable final long id) {
        log.debug("remove owner with required id:{}",id);
        return service.remove(id);
    }
}
