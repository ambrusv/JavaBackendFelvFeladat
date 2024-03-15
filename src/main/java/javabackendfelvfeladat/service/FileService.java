package javabackendfelvfeladat.service;

import javabackendfelvfeladat.domain.FileEntity;
import javabackendfelvfeladat.dto.FileUploadDTO;
import javabackendfelvfeladat.exception.ImageNotFoundException;
import javabackendfelvfeladat.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    public void uploadImage(FileUploadDTO postFile) {
        List<MultipartFile> images = postFile.getImages();

        for (MultipartFile image : images) {
            try {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName(image.getOriginalFilename());
                fileEntity.setData(image.getBytes());

                fileRepository.save(fileEntity);
            } catch (IOException e) {
                log.error("Failed to read image data: {}", e.getMessage());
            }
        }
    }

    public byte[] downloadImage(String fileName) {
        try {
            Optional<FileEntity> imageOptional = fileRepository.findByFileName(fileName);
            FileEntity fileEntity = imageOptional.get();
            return fileEntity.getData();
        } catch (Exception e) {
            log.error("Image not found with filename: " + fileName);
            throw new ImageNotFoundException("Image not found with filename: " + fileName);
        }
    }

    public ResponseEntity<InputStreamResource> createImageResponse(String fileName, byte[] fileData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDispositionFormData("attachment", fileName);

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileData));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }

}