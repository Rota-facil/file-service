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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final FileService fileService;

    @PostMapping("/me/profile")
    public ResponseEntity<FileResponseDTO> uploadProfilePic(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.uploadProfilePic(currentUser, multipartFile));
    }

    @PostMapping("/me/documents")
    public ResponseEntity<FileResponseDTO> uploadDocuments(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.upload(currentUser, multipartFile, FileCategory.DOCUMENT));
    }

    @GetMapping("/me/profile")
    public ResponseEntity<FileResponseDTO> fetchProfile(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(fileService.fetchProfile(currentUser));
    }

    @GetMapping("/me/documents")
    public ResponseEntity<List<FileResponseDTO>> listDocuments(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(fileService.list(currentUser, FileCategory.DOCUMENT));
    }

    @GetMapping("/me/documents/{documentId}")
    public ResponseEntity<FileResponseDTO> fetchDocument(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable UUID documentId) {
        return ResponseEntity.ok(fileService.fetch(currentUser, documentId));
    }

    @PutMapping("me/documents/{documentId}")
    public ResponseEntity<FileResponseDTO> updateDocument(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID documentId,
            MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.update(currentUser, documentId, multipartFile));
    }

    @DeleteMapping("me/profile/{profileId}")
    public ResponseEntity<Void> deleteProfilePic(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID profileId
    ) {
        fileService.delete(currentUser, profileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("me/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID documentId
    ) {
        fileService.delete(currentUser, documentId);
        return ResponseEntity.ok().build();
    }
}
