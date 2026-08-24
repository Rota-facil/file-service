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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UploadProfilePicUseCase uploadProfilePicUseCase;
    private final UploadFileUseCase uploadFileUseCase;
    private final FetchProfilePicUseCase fetchProfilePicUseCase;
    private final FetchFileByCurrentUserAndIdUseCase fetchFileByCurrentUserAndIdUseCase;
    private final UpdateFileUseCase updateFileUseCase;
    private final ListFilesByCurrentUseAndCategoryUseCase listFilesByCurrentUseAndCategoryUseCase;
    private final DeleteFileUseCase deleteFileUseCase;


    @PostMapping("/me/profile")
    public ResponseEntity<FileResponseDTO> uploadProfilePic(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(uploadProfilePicUseCase.execute(currentUser, multipartFile));
    }

    @PostMapping("/me/documents")
    public ResponseEntity<FileResponseDTO> uploadDocuments(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(uploadFileUseCase.execute(currentUser, multipartFile, FileCategory.DOCUMENT));
    }

    @GetMapping("/me/profile")
    public ResponseEntity<FileResponseDTO> fetchProfile(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(fetchProfilePicUseCase.execute(currentUser));
    }

    @GetMapping("/me/documents")
    public ResponseEntity<List<FileResponseDTO>> listDocuments(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(listFilesByCurrentUseAndCategoryUseCase.execute(currentUser, FileCategory.DOCUMENT));
    }

    @GetMapping("/me/documents/{documentId}")
    public ResponseEntity<FileResponseDTO> fetchDocument(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable UUID documentId) {
        return ResponseEntity.ok(fetchFileByCurrentUserAndIdUseCase.execute(currentUser, documentId));
    }

    @PutMapping("me/documents/{documentId}")
    public ResponseEntity<FileResponseDTO> updateDocument(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID documentId,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(updateFileUseCase.execute(currentUser, documentId, multipartFile));
    }

    @DeleteMapping("me/profile/{profileId}")
    public ResponseEntity<Void> deleteProfilePic(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID profileId
    ) {
        deleteFileUseCase.execute(currentUser, profileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("me/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID documentId
    ) {
        deleteFileUseCase.execute(currentUser, documentId);
        return ResponseEntity.ok().build();
    }
}
