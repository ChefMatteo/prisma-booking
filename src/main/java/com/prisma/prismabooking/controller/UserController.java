package com.prisma.prismabooking.controller;

import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.User;
import com.prisma.prismabooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get a page of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the page",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedResponse.class)) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<User> findUserPage(@RequestParam(required = false) Integer offset,
                                                 @RequestParam(required = false) Integer limit) {
        return userService.findUsersPage(offset, limit);
    }

    @Operation(summary = "Find a user by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content )})
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@PathVariable("userId") String userId) {
        return userService.getSingle(userId);
    }

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "User"))}),
            @ApiResponse(responseCode = "409", description = "Already exists a user with id in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.createNew(user);
    }

    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "User"))}),
            @ApiResponse(responseCode = "404", description = "No user to update", content = @Content)})
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@PathVariable("userId") String userId,
                                @RequestBody User user) {
        return  userService.updateSingle(user, userId);
    }

    @Operation(summary = "Delete an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No user to delete")})
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteSingle(userId);
    }

}
