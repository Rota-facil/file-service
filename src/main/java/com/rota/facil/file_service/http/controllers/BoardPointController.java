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
@RequestMapping("/board-points")
@RequiredArgsConstructor
public class BoardPointController {
    private final FetchFileByIdAndCategoryUseCase fetchFileByIdAndCategoryUseCase;
    private final ListFileByCategory listFileByCategory;
    private final ListFileByOwnerAndCategoryUseCase listFileByOwnerAndCategoryUseCase;
    private final UpdateFileUseCase updateFileUseCase;
    private final UploadFileWithOwnerUseCase uploadFileWithOwnerUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @PostMapping("/{boardPointId}")
    public ResponseEntity<FileResponseDTO> uploadBoardPointPhotos(
            @PathVariable UUID boardPointId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(uploadFileWithOwnerUseCase.execute(currentUser, boardPointId, multipartFile, FileCategory.BOARD_POINT_PIC));
    }

    @GetMapping
    public ResponseEntity<List<FileResponseDTO>> listAllBoardPointPhotos() {
        return ResponseEntity.ok(listFileByCategory.execute(FileCategory.BOARD_POINT_PIC));
    }

    @GetMapping("/{boardPointId}/all")
    public ResponseEntity<List<FileResponseDTO>> listBoardPointPhotos(@PathVariable UUID boardPointId) {
        return ResponseEntity.ok(listFileByOwnerAndCategoryUseCase.execute(boardPointId, FileCategory.BOARD_POINT_PIC));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> fetchBoardPointPhoto(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fetchFileByIdAndCategoryUseCase.execute(fileId, FileCategory.BOARD_POINT_PIC));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponseDTO> updateBoardPointPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestPart(name = "file") MultipartFile multipartFile
    ) {
        return ResponseEntity.ok(updateFileUseCase.execute(currentUser, fileId, multipartFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteBoardPointPhoto(
            @PathVariable UUID fileId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        deleteFileUseCase.execute(currentUser, fileId);
        return ResponseEntity.ok().build();
    }
}
