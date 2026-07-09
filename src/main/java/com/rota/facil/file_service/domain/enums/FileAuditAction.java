package com.rota.facil.file_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileAuditAction {
    FILE_CREATED("CREATE", "%s fez upload do arquivo %s"),
    FILE_UPDATED("UPDATE", "%s atualizou o arquivo %s"),
    FILE_DELETED("DELETE", "%s deletou o arquivo %s");

    private final String actionType;
    private final String titleTemplate;

    public String title(String actorEmail, String fileName) {
        return this.titleTemplate.formatted(actorEmail, fileName);
    }
}
