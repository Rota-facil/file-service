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
@RequestMapping("/board-points")
@RequiredArgsConstructor
public class BoardPointController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<FileResponseDTO> uploadBoardPointPhotos(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.upload(currentUser, multipartFile, FileCategory.BOARD_POINT_PIC));
    }

    @GetMapping
    public ResponseEntity<List<FileResponseDTO>> listAllBoardPointPhotos() {
        return ResponseEntity.ok(fileService.listByCategory(FileCategory.BOARD_POINT_PIC));
    }

    @GetMapping("/{boardPointId}/all")
    public ResponseEntity<List<FileResponseDTO>> listBoardPointPhotos(@PathVariable UUID boardPointId) {
        return ResponseEntity.ok(fileService.listByOwnerIdAndCategory(boardPointId, FileCategory.BOARD_POINT_PIC));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchBoardPointPhoto(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fileService.fetchByIdAndCategory(fileId, FileCategory.BOARD_POINT_PIC));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateBoardPointPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(fileService.update(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteBoardPointPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        fileService.delete(currentUser, fileId);
        return ResponseEntity.ok().build();
    }
}
