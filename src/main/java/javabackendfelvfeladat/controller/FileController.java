package javabackendfelvfeladat.controller;

import javabackendfelvfeladat.dto.FileUploadDTO;
import javabackendfelvfeladat.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
@Slf4j
public class FileController {

    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@ModelAttribute @Valid FileUploadDTO postFile) {
        try {
            fileService.uploadImage(postFile);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully");
        } catch (Exception e) {
            log.error("Failed to upload Image", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload Image: " + e.getMessage());
        }
    }

}
