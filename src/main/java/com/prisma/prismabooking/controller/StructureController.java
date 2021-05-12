package com.prisma.prismabooking.controller;

import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.Structure;
import com.prisma.prismabooking.service.StructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/structures")
public class StructureController {

    private final StructureService structureService;

    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @Operation(summary = "Get structures list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedResponse.class))})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<Structure> findStructurePage(@RequestParam(required = false) Integer offset,
                                                      @RequestParam(required = false) Integer limit) {
        return structureService.findStructurePage(offset, limit);
    }

    @Operation(summary = "Get an existing structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Structure"))}),
            @ApiResponse(responseCode = "404", description = "No structure to get", content = @Content)})
    @GetMapping("/{structureId}")
    @ResponseStatus(HttpStatus.OK)
    public Structure findStructure(@PathVariable("structureId") String structureId) {
        return structureService.findStructure(structureId);
    }

    @Operation(summary = "Add a new structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Structure"))}),
            @ApiResponse(responseCode = "409", description = "Already exists a structure with id in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Structure createStructure(@RequestBody Structure structure) {
        return structureService.createStructure(structure);
    }

    @Operation(summary = "Update an existing structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Structure successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Structure"))}),
            @ApiResponse(responseCode = "404", description = "No structure to update", content = @Content)})
    @PutMapping("/{structureId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Structure updateStructure(@PathVariable("structureId") String structureId,
                                     @RequestBody Structure structure) {
        return structureService.updateStructure(structureId, structure);
    }

    @Operation(summary = "Delete an existing structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No structure to delete")})
    @DeleteMapping("/{structureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStructure(@PathVariable("structureId") String structureId) {
        structureService.delete(structureId);
    }

}
