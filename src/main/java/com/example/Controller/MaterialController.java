package com.example.Controller;

import com.example.entities.Material;
import com.example.service.MaterialService;
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
@Slf4j
@RequestMapping("materials")
public class MaterialController {
    private final MaterialService service;

    @Autowired
    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get materials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found materials", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Material> getAllMaterial(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page")final int page,
                                             @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size")final int pageSize) {
        log.debug("get all materials with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page,pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found material by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "material not found", content = {@Content(mediaType = "application/json")})})
    public Material getByIdMaterial(@Parameter(description = "material id") @PathVariable final long id) {
        log.debug("get material with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "material updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Material updateMaterial(@Parameter(description = "material id")@PathVariable final long id,
                         @Parameter(description = "material body")@RequestBody @Valid final Material material) {
        log.debug("update material with required id:{} and branch:{}",id,material);
        return service.update(id, material);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created material", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Material createMaterial(@Parameter(description = "material body")@RequestBody @Valid final Material material) {
        log.debug("creat material with required branch:{}",material);
        return service.create(material);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed material", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "material not found", content = {@Content(mediaType = "application/json")})})
    public Material removeMaterial(@Parameter(description = "material id")@PathVariable final long id) {
        log.debug("remove material with required id:{}",id);
        return service.remove(id);
    }
}
