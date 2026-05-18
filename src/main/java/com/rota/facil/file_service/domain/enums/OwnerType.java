package com.rota.facil.file_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OwnerType {
    USER("/users"),
    BUS("/bus"),
    INSTITUTION("/institutions"),
    BOARD_POINT("/board_points"),
    ROUTE("/routes");

    private final String path;
}
