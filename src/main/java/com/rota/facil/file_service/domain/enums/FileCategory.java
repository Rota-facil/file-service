package com.rota.facil.file_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileCategory {
    PROFILE_PIC("/profiles", OwnerType.USER),
    DOCUMENT("/documents", OwnerType.USER),
    BUS_PHOTO("/bus", OwnerType.BUS),
    INSTITUTION_PIC("/pictures", OwnerType.INSTITUTION),
    BOARD_POINT_PIC("/pictures", OwnerType.BOARD_POINT);

    private final String path;
    private final OwnerType ownerType;
}
