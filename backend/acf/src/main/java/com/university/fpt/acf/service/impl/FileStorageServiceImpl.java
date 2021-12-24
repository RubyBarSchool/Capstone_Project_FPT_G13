package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.File;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.repository.FileRepository;
import com.university.fpt.acf.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path root = Paths.get("uploads");

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    //************************************
    // create Directory to save file
    //************************************
    @Override
    public void init() {
        try {
            if(!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    //************************************
    // Save file
    //************************************
    @Override
    public void save(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            File file1 = new File();
            AccountSercurity accountSercurity = new AccountSercurity();
            file1.setModified_by(accountSercurity.getUserName());
            file1.setCreated_by(accountSercurity.getUserName());
            file1.setName(fileName);
            file1.setType(file.getContentType());
            file1.setUrl(root.toUri().getPath());
            fileRepository.save(file1);
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
    //************************************
    // Load file
    //************************************
    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    //************************************
    // Get uri of file
    //************************************
    @Override
    public String loadUri(String filename) {
        try {
            Path file = root.resolve(filename);
            return file.toUri().getPath();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    //************************************
    // Delete all file of Directory
    //************************************
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }
    //************************************
    // Save image of Directory
    //************************************
    @Override
    public File saveImage(MultipartFile file) {
        File file1 = new File();
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy_hh-mm-ss");
            String dateTimeAfterFormat = dateTime.format(dateTimeFormat);
            String fileName = "file_" + dateTimeAfterFormat + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            AccountSercurity accountSercurity = new AccountSercurity();
            file1.setModified_by(accountSercurity.getUserName());
            file1.setCreated_by(accountSercurity.getUserName());
            file1.setName(fileName);
            file1.setType(file.getContentType());
            file1.setUrl(root.toUri().getPath());
            file1 = fileRepository.save(file1);
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return file1;
    }
    //************************************
    // Delete file by fileId
    //************************************
    @Override
    public Boolean deleteFile(String fileId) {
        Boolean check = false;
        try {
            Employee employee = employeeRepository.getEmployeeByFile(fileId);
            String nameFile = "";
            if(employee != null){
                if(employee.getImage() != null){
                    nameFile = employee.getImage().getName();
                }
                employee.setImage(null);
                employeeRepository.save(employee);
            }
            fileRepository.deleteByName(nameFile);

            Path file = root.resolve(nameFile);
            FileSystemUtils.deleteRecursively(file);

            check = true;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return check;
    }
}
