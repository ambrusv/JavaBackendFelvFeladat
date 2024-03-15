package javabackendfelvfeladat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Entity
@Table(name = "images")
@Setter
@Getter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;

}
