package javabackendfelvfeladat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    public ResizeResultService resizeImage(MultipartFile file, int maxWidth, int maxHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        int newWidth;
        int newHeight;
        if (originalImage.getWidth() > originalImage.getHeight()) {
            newWidth = maxWidth;
            newHeight = (int) Math.round((double) originalImage.getHeight() / originalImage.getWidth() * maxWidth);
        } else {
            newHeight = maxHeight;
            newWidth = (int) Math.round((double) originalImage.getWidth() / originalImage.getHeight() * maxHeight);
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);

        return new ResizeResultService(outputStream.toByteArray(), newWidth, newHeight);
    }
}
