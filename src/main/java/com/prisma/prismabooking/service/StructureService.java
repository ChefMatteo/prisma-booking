package com.prisma.prismabooking.service;

import com.google.gson.Gson;
import com.prisma.prismabooking.component.ConfigurationComponent;
import com.prisma.prismabooking.model.PagedResponse;
import com.prisma.prismabooking.model.Structure;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class StructureService extends BaseService<Structure> {

    public static Resource structureFile;
    RoomService roomService;

    public StructureService(ConfigurationComponent configurationComponent, Gson gson) {
        super.config = configurationComponent;
        super.gson = gson;
        super.getterOfPrimaryKey = "getId";
    }

    @Autowired
    public void setStructureService(RoomService roomService){
        this.roomService = roomService;
    }


    @PostConstruct
    private void init() {
        structureFile = init(Structure.class, "/structures.json");
    }

    public PagedResponse<Structure> findStructurePage(Integer offset, Integer limit) {
        return findPage(list, offset, limit);
    }

    public Structure createStructure(Structure structure) {
        return createNew(structure, structureFile);
    }

    public Structure updateStructure(String structureId, Structure structure) {
        if(!structureId.equalsIgnoreCase(structure.getId())){
            roomService.list.stream()
                    .filter(r->r.getStructureId().equalsIgnoreCase(structureId))
                    .forEach(r->r.setStructureId(structure.getId()));
        }
        return updateSingle(structure, structureId, structureFile);
    }

    public void delete(String structureId) {
        var roomsToRemove = roomService.list.stream()
                .filter(r->r.getStructureId().equalsIgnoreCase(structureId))
                .collect(Collectors.toList());
        roomService.list.retainAll(roomsToRemove);
        roomService.updateJson(RoomService.roomFile);
        deleteSingle(structureId, structureFile);
    }

    public Structure findStructure(String structureId) {
        return getSingle(structureId);
    }
}
