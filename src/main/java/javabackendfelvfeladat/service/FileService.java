package javabackendfelvfeladat.service;

import javabackendfelvfeladat.domain.FileEntity;
import javabackendfelvfeladat.dto.FileUploadDTO;
import javabackendfelvfeladat.exception.ImageNotFoundException;
import javabackendfelvfeladat.exception.ImageZipCreationException;
import javabackendfelvfeladat.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class FileService {
    private static final int MAX_WIDTH = 5000;
    private static final int MAX_HEIGHT = 5000;
    private final FileRepository fileRepository;
    private ImageService imageService;
    public void uploadImage(FileUploadDTO postFile) {
        List<MultipartFile> images = postFile.getImages();

        for (MultipartFile image : images) {
            try {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName(image.getOriginalFilename());
                fileEntity.setData(image.getBytes());
                fileEntity.setWidth(calculateWidth(fileEntity));
                fileEntity.setHeight(calculateHeight(fileEntity));

                if (fileEntity.getWidth() > MAX_WIDTH || fileEntity.getHeight() > MAX_HEIGHT) {
                    byte[] resizedImageData = imageService.resizeImage(image, 5000, 5000);
                    fileEntity.setData(resizedImageData);

                    fileEntity.setWidth(5000);
                    fileEntity.setHeight(5000);
                }

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

    public byte[] createZipFile() {
        try {
            List<FileEntity> images = getAllImages();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (FileEntity image : images) {
                ZipEntry entry = new ZipEntry(image.getFileName());
                zos.putNextEntry(entry);
                IOUtils.copy(new ByteArrayInputStream(image.getData()), zos);
                zos.closeEntry();
            }

            zos.close();

            return baos.toByteArray();
        } catch (Exception e) {
            log.error("Failed to create ZIP file for images", e);
            throw new ImageZipCreationException("Failed to create ZIP file for images", e);
        }
    }
    private int calculateWidth(FileEntity fileEntity) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileEntity.getData()));
        return image.getWidth();
    }
    private int calculateHeight(FileEntity fileEntity) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileEntity.getData()));
        return image.getHeight();
    }
    private List<FileEntity> getAllImages() {
        return fileRepository.findAll();
    }

}