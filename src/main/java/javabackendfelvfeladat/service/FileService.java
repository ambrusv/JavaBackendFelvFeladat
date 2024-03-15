package javabackendfelvfeladat.service;

import javabackendfelvfeladat.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class FileService {

    private FileRepository fileRepository;
    private final ModelMapper modelMapper;
}
