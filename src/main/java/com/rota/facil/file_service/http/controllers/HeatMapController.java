package com.rota.facil.file_service.http.controllers;

import com.rota.facil.file_service.business.files.*;
import com.rota.facil.file_service.domain.enums.FileCategory;
import com.rota.facil.file_service.http.dto.request.CurrentUser;
import com.rota.facil.file_service.http.dto.response.file.FileResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/heat-map")
@RequiredArgsConstructor
public class HeatMapController {
    private final UploadFileWithOwnerUseCase uploadFileWithOwnerUseCase;
    private final ListFileByOwnerAndCategoryUseCase listFileByOwnerAndCategoryUseCase;
    private final FetchFileByIdAndCategoryUseCase fetchFileByIdAndCategoryUseCase;
    private final DeleteFileUseCase deleteFileUseCase;
    private final UpdateFileUseCase updateFileUseCase;

    @PostMapping("/{routeId}")
    public ResponseEntity<FileResponseDTO> uploadHeatMap(
            @PathVariable UUID routeId,
            @RequestPart(name = "file") MultipartFile file,
            @AuthenticationPrincipal CurrentUser currentUser
            ) {
        return ResponseEntity.ok(uploadFileWithOwnerUseCase.execute(currentUser, routeId, file, FileCategory.ROUTE_BOARD_POINT_HEAT_MAP));
    }

    @GetMapping("/{routeId}/all")
    public ResponseEntity<List<FileResponseDTO>> listAllRouteHeatMaps(
            @PathVariable UUID routeId
    ) {
        return ResponseEntity.ok(listFileByOwnerAndCategoryUseCase.execute(routeId, FileCategory.ROUTE_BOARD_POINT_HEAT_MAP));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchHeatPhoto(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fetchFileByIdAndCategoryUseCase.execute(fileId, FileCategory.ROUTE_BOARD_POINT_HEAT_MAP));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateHeatMap(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(updateFileUseCase.execute(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteHeatMap(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        deleteFileUseCase.execute(currentUser, fileId);
        return ResponseEntity.ok().build();
    }
}
