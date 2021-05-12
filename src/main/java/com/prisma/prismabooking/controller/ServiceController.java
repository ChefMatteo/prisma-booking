package com.prisma.prismabooking.controller;

import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.Service;
import com.prisma.prismabooking.model.room.Room;
import com.prisma.prismabooking.service.RoomService;
import com.prisma.prismabooking.service.ServiceService;
import com.prisma.prismabooking.utils.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sevices")
public class ServiceController {

    final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Operation(summary = "Get a page of services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedResponse.class))})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<Service> findServicePage(@RequestParam(required = false) Integer offset,
                                                  @RequestParam(required = false) Integer limit) {
        return serviceService.findServicePage(offset, limit);
    }

    @Operation(summary = "Find a service by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Service"))}),
            @ApiResponse(responseCode = "404", description = "No service to get", content = @Content)})
    @GetMapping("/{serviceName}")
    @ResponseStatus(HttpStatus.OK)
    public Service findStructure(@PathVariable("serviceName") String serviceName) {
        return serviceService.findService(serviceName);
    }

    @Operation(summary = "Create new service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Service.class)) }),
            @ApiResponse(responseCode = "409", description = "Already exists a service with name in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Service createStructure(@RequestBody Service service) {
        return serviceService.createService(service);
    }

    @Operation(summary = "Update an existing service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Service"))}),
            @ApiResponse(responseCode = "404", description = "No service to update", content = @Content)})
    @PutMapping("/{serviceName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Service updateStructure(@PathVariable("serviceName") String serviceName,
                                @RequestBody Service service) {
        return serviceService.updateService(serviceName, service);
    }

    @Operation(summary = "Delete an existing service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No service to delete")})
    @DeleteMapping("/{serviceName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable("serviceName") String serviceName) {
        serviceService.deleteFromFile(serviceName);
    }

}
