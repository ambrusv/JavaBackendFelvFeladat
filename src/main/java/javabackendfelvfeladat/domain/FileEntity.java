package javabackendfelvfeladat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

}
