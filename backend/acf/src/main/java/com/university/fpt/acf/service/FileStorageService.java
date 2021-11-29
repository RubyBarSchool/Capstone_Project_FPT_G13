package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
     void init();

     void save(MultipartFile file);

     Resource load(String filename);

     String loadUri(String filename);

     void deleteAll();

     Stream<Path> loadAll();

     File saveImage(MultipartFile file);

     Boolean deleteFile(String fileId);
}
