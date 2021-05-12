package com.prisma.prismabooking.controller;

import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.room.Room;
import com.prisma.prismabooking.service.RoomService;
import com.prisma.prismabooking.utils.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/structures/{structureId}/rooms")
public class RoomController {

    final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get a page of structure's rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the page",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedResponse.class)) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<Room> findRoomPage(@PathVariable("structureId") String structureId,
                                                 @RequestParam(required = false) Integer offset,
                                                 @RequestParam(required = false) Integer limit) {
        return roomService.findStructureRoomPage(structureId, offset, limit);
    }

    @Operation(summary = "Find a room by structureId and roomId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the room",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "404", description = "Room not found",content = @Content )})
    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Room findRoom(@PathVariable("structureId") String structureId,
                              @PathVariable("roomId") String roomId) {
        return roomService.findRoom(structureId, roomId);
    }

    @Operation(summary = "Create new room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)) }),
            @ApiResponse(responseCode = "409", description = "Already exists a room with id in body", content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@PathVariable("structureId") String structureId,
                                @RequestBody Room room) {
        if(room.getId() != null)
            throw new BadRequestException("Cannot POST an existing resource");
        return roomService.createRoom(structureId, room);
    }

    @Operation(summary = "Update an existing room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", ref = "Room"))}),
            @ApiResponse(responseCode = "404", description = "No room to update", content = @Content)})
    @PutMapping("/{roomId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Room updateRoom(@PathVariable("roomId") String roomId,
                                @RequestBody Room room) {
        return roomService.updateSingle(room, roomId);
    }

    @Operation(summary = "Delete an existing room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No room to delete", content = @Content)})
    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable("roomId") String roomId) {
        roomService.deleteSingle(roomId);
    }

}
