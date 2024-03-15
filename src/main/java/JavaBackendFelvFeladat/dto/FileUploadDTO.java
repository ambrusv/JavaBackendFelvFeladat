package JavaBackendFelvFeladat.dto;

import JavaBackendFelvFeladat.validator.Image;
import JavaBackendFelvFeladat.validator.NotEmptyList;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileUploadDTO {
    @NotEmptyList(message = "Images list must not be empty")
    @Image(message = "Only JPG/PNG/JPEG accepted")
    private List<MultipartFile> images;
}
