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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {
    private final FileService fileService;

    @PostMapping("/{busId}")
    public ResponseEntity<FileResponseDTO> uploadBusPhotos(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.upload(currentUser, multipartFile, FileCategory.BUS_PHOTO));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<List<FileResponseDTO>> listBusPhotos() {
        return ResponseEntity.ok(fileService.listByCategory(FileCategory.BUS_PHOTO));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchBusPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ResponseEntity.ok(fileService.fetch(currentUser, fileId));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateBusPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.update(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteBusPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        fileService.delete(currentUser, fileId);
        return ResponseEntity.ok().build();
    }
}
