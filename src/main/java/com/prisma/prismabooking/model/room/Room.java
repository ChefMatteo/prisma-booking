package com.prisma.prismabooking.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    private String id;
    private String structureId;
    private String name;
    private Integer singleBeds;
    private Integer kingSizeBeds;
    private Integer baths;
    private Boolean bathTub;
    private Boolean shower;
    private RoomType type;
    private Map<Season, Double> prices;
}
