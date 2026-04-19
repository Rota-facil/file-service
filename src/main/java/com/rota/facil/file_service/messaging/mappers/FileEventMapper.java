package com.rota.facil.file_service.messaging.mappers;

import com.rota.facil.file_service.domain.enums.ActionType;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.messaging.dto.send.FileEventSend;
import com.rota.facil.file_service.persistence.entities.FileEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FileEventMapper {
    @Mapping(target = "actionTitle", expression = "java(currentUser.email() + actionType.getTitle() + entity.getOriginalFilename())")
    @Mapping(target = "resourceName", expression = "java(ResourceName.FILE)")
    FileEventSend map(FileEntity entity, CurrentUser currentUser, ActionType actionType);
}
