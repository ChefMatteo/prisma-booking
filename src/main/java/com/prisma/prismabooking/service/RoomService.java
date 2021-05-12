package com.prisma.prismabooking.service;

import com.google.gson.Gson;
import com.prisma.prismabooking.component.ConfigurationComponent;
import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.room.Room;
import com.prisma.prismabooking.utils.NotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomService extends BaseService<Room> {

    public static Resource roomFile;
    StructureService structureService;

    public RoomService(ConfigurationComponent configurationComponent, Gson gson) {
        super.config = configurationComponent;
        super.gson = gson;
        super.getterOfPrimaryKey = "getId";
    }

    @Autowired
    public void setStructureService(StructureService structureService){
        this.structureService = structureService;
    }

    @PostConstruct
    private void init() {
        roomFile = init(Room.class, "/rooms.json");
    }

    public Room findRoom(String structureId, String roomId) {
        return list.stream()
                .filter(s -> s.getStructureId().equalsIgnoreCase(structureId))
                .filter(s -> s.getId().equalsIgnoreCase(roomId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(roomId));
    }

    @SneakyThrows
    public Room createRoom(String structureId, Room room) {
/*
        if(room!=null) {
            if (list.stream()
                    .anyMatch(r -> r.getStructureId().equalsIgnoreCase(structureId) &&
                            r.getId().equalsIgnoreCase(room.getId()))) {
                deleteFromFile(structureId, room.getId());
                list.add(room);
                Files.write(persistenceFile, gson.toJson(room).concat(System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                return room;
            }
            list.add(room);
            Files.write(persistenceFile, gson.toJson(room).concat(System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            return room;
        }
        else throw new BadRequestException("Room is null");
*/
        room.setStructureId(structureId);
        return createNew(room, roomFile);
    }

    @SneakyThrows
    public void deleteFromFile(String roomId) {
        deleteSingle(roomId, roomFile);
    }


    public PagedResponse<Room> findStructureRoomPage(String structureId, Integer offset, Integer limit) {
        List<Room> filteredList = list.stream()
                .filter(r->r.getStructureId().equalsIgnoreCase(structureId))
                .collect(Collectors.toList());
        return findPage(filteredList, offset, limit);
    }

    public Room updateRoom(String roomId, Room room) {
        return updateSingle(room, roomId, roomFile);
    }
}
