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
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<FileResponseDTO> uploadInstitutionPhotos(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.upload(currentUser, multipartFile, FileCategory.INSTITUTION_PIC));
    }

    @GetMapping
    public ResponseEntity<List<FileResponseDTO>> listAllInstitutionPhotos() {
        return ResponseEntity.ok(fileService.listByCategory(FileCategory.INSTITUTION_PIC));
    }

    @GetMapping("/{institutionId}/all")
    public ResponseEntity<List<FileResponseDTO>> listInstitutionPhotos(@PathVariable UUID institutionId) {
        return ResponseEntity.ok(fileService.listByOwnerIdAndCategory(institutionId, FileCategory.INSTITUTION_PIC));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchInstitutionPhoto(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fileService.fetchByIdAndCategory(fileId, FileCategory.INSTITUTION_PIC));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateInstitutionPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.update(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteInstitutionPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        fileService.delete(currentUser, fileId);
        return ResponseEntity.ok().build();
    }

}
