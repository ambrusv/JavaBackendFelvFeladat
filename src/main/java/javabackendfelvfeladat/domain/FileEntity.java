package javabackendfelvfeladat.domain;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Setter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "base64_data", nullable = true)
    private String base64Data;

}
