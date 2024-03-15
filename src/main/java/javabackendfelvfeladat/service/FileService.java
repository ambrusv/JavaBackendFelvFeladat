package javabackendfelvfeladat.service;

import javabackendfelvfeladat.domain.FileEntity;
import javabackendfelvfeladat.dto.FileUploadDTO;
import javabackendfelvfeladat.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    public void uploadImage(FileUploadDTO postFile) {
        List<MultipartFile> images = postFile.getImages();

        for (MultipartFile image : images) {
            try {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName(image.getOriginalFilename());
                fileEntity.setData(image.getBytes());
                fileEntity.setBase64Data(Base64.getEncoder().encodeToString(image.getBytes()));

                fileRepository.save(fileEntity);
            } catch (IOException e) {
                log.error("Failed to read image data: {}", e.getMessage());
            }
        }
    }
}