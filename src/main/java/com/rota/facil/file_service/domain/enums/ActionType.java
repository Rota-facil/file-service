package com.rota.facil.file_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    CREATE(" Fez upload de arquivo "),
    UPDATE(" Atualizou arquivo "),
    DELETE(" Deletou arquivo ");

    private final String title;
}
