package javabackendfelvfeladat.dto;

import javabackendfelvfeladat.validator.Image;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileUploadDTO {
    @Image(message = "Only JPG/PNG accepted")
    private List<MultipartFile> images;
}
