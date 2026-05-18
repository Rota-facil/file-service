package com.rota.facil.file_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileCategory {
    PROFILE_PIC("/profiles", OwnerType.USER),
    DOCUMENT("/documents", OwnerType.USER),
    BUS_PHOTO("/bus", OwnerType.BUS),
    INSTITUTION_PIC("/pictures/institutions", OwnerType.INSTITUTION),
    BOARD_POINT_PIC("/pictures/board_points/", OwnerType.BOARD_POINT),
    ROUTE_BOARD_POINT_HEAT_MAP("/board_points/heat_map", OwnerType.ROUTE);

    private final String path;
    private final OwnerType ownerType;
}
