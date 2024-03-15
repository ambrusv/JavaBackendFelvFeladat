package javabackendfelvfeladat.controller;

import javabackendfelvfeladat.dto.FileUploadDTO;
import javabackendfelvfeladat.exception.ImageNotFoundException;
import javabackendfelvfeladat.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
        try {
            byte[] fileData = fileService.downloadImage(fileName);

            if (fileData != null) {
                return fileService.createImageResponse(fileName, fileData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ImageNotFoundException e) {
            log.error("Image not found with filename: {}", fileName);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Failed to download image", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
