package javabackendfelvfeladat.service;

import lombok.Getter;

@Getter
public class ResizeResultService {
    private final byte[] resizedImageData;
    private final int width;
    private final int height;

    public ResizeResultService(byte[] resizedImageData, int width, int height) {
        this.resizedImageData = resizedImageData;
        this.width = width;
        this.height = height;
    }

}
