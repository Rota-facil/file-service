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
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {
    private final UploadFileWithOwnerUseCase uploadFileWithOwnerUseCase;
    private final DeleteFileUseCase deleteFileUseCase;
    private final FetchFileByCurrentUserAndIdUseCase fetchFileByCurrentUserAndIdUseCase;
    private final ListFileByCategory listFileByCategory;
    private final UpdateFileUseCase updateFileUseCase;

    @PostMapping("/{busId}")
    public ResponseEntity<FileResponseDTO> uploadBusPhotos(
            @PathVariable UUID busId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(uploadFileWithOwnerUseCase.execute(currentUser, busId, multipartFile, FileCategory.BUS_PHOTO));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<List<FileResponseDTO>> listBusPhotos() {
        return ResponseEntity.ok(listFileByCategory.execute(FileCategory.BUS_PHOTO));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchBusPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ResponseEntity.ok(fetchFileByCurrentUserAndIdUseCase.execute(currentUser, fileId));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateBusPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(updateFileUseCase.execute(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteBusPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        deleteFileUseCase.execute(currentUser, fileId);
        return ResponseEntity.ok().build();
    }
}
