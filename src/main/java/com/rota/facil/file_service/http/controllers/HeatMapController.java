package com.rota.facil.file_service.http.controllers;

import com.rota.facil.file_service.business.FileService;
import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/heat-map")
@RequiredArgsConstructor
public class HeatMapController {
    private final FileService fileService;

    @PostMapping("/{routeId}")
    public ResponseEntity<FileResponseDTO> uploadHeatMap(
            @PathVariable UUID routeId,
            @RequestPart(name = "file") MultipartFile file,
            @AuthenticationPrincipal CurrentUser currentUser
            ) {
        return ResponseEntity.ok(fileService.upload(currentUser, routeId, file, FileCategory.ROUTE_BOARD_POINT_HEAT_MAP));
    }
}
