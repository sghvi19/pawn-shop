package com.example.Controller;

import com.example.entities.Branch;
import com.example.service.BranchService;
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
@RequestMapping("branches")
@Slf4j
public class BranchController {

    private final BranchService service;

    @Autowired
    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get branches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found branches", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Branch> getAllBranch(@Parameter(description = "page") @RequestParam(required = false, defaultValue = "${page}", name = "page")final int page,
                                          @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size")final int pageSize) {
        log.debug("get all branches with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page,pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found branch by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "branch not found", content = {@Content(mediaType = "application/json")})})
    public Branch getByIdBranch(@Parameter(description = "branch id") @PathVariable final long id) {
        log.debug("get branch with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "branch updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Branch updateBranch(@Parameter(description = "branch id")@PathVariable final long id,
                         @Parameter(description = "branch body")@RequestBody @Valid final Branch branch) {
        log.debug("update branch with required id:{} and branch:{}",id,branch);
        return service.update(id, branch);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created branch", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Branch createBranch(@Parameter(description = "branch body")@RequestBody @Valid final Branch branch) {
        log.debug("creat branch with required branch:{}",branch);
        return service.create(branch);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed branch", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "branch not found", content = {@Content(mediaType = "application/json")})})
    public Branch removeBranch(@Parameter(description = "branch id")@PathVariable final long id) {
        log.debug("remove branch with required id:{}",id);
        return service.remove(id);
    }
}
