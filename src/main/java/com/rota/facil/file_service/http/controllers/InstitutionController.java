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
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {
    private final UploadFileWithOwnerUseCase uploadFileWithOwnerUseCase;
    private final FetchFileByIdAndCategoryUseCase fetchFileByIdAndCategoryUseCase;
    private final ListFileByCategory listFileByCategory;
    private final ListFileByOwnerAndCategoryUseCase listFileByOwnerAndCategoryUseCase;
    private final UpdateFileUseCase updateFileUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @PostMapping("/{institutionId}")
    public ResponseEntity<FileResponseDTO> uploadInstitutionPhotos(
            @PathVariable UUID institutionId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(uploadFileWithOwnerUseCase.execute(currentUser, institutionId, multipartFile, FileCategory.INSTITUTION_PIC));
    }

    @GetMapping
    public ResponseEntity<List<FileResponseDTO>> listAllInstitutionPhotos() {
        return ResponseEntity.ok(listFileByCategory.execute(FileCategory.INSTITUTION_PIC));
    }

    @GetMapping("/{institutionId}/all")
    public ResponseEntity<List<FileResponseDTO>> listInstitutionPhotos(@PathVariable UUID institutionId) {
        return ResponseEntity.ok(listFileByOwnerAndCategoryUseCase.execute(institutionId, FileCategory.INSTITUTION_PIC));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchInstitutionPhoto(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fetchFileByIdAndCategoryUseCase.execute(fileId, FileCategory.INSTITUTION_PIC));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateInstitutionPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(updateFileUseCase.execute(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteInstitutionPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        deleteFileUseCase.execute(currentUser, fileId);
        return ResponseEntity.ok().build();
    }

}
