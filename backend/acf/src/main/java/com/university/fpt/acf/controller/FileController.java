package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.File;
import com.university.fpt.acf.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/files")
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;
    //************************************
    // Upload file
    //************************************
    @PostMapping("/upload")
    public ResponseEntity<ResponseCommon> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            fileStorageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            responseCommon.setData(true);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Upload file image
    //************************************
    @PostMapping("/image")
    public ResponseEntity<ResponseCommon> uploadImage(@RequestParam("file") MultipartFile file) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            File file1 = fileStorageService.saveImage(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            responseCommon.setData(file1.getId());
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() ;
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Read excel
    //************************************

    @PostMapping("/readexcel")
    public ResponseEntity<ResponseCommon> readExcel(@RequestParam("file") MultipartFile file) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            fileStorageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            responseCommon.setData(true);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() ;
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }

    //************************************
    // Get file by name
    //************************************
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        try {
            Resource file = fileStorageService.load(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }catch (Exception exception){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "null" + "\"").body(null);
        }

    }
    //************************************
    // Delete file by name
    //************************************
    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<ResponseCommon> deleteFile(@PathVariable String filename) throws IOException {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        try {
            check = fileStorageService.deleteFile(filename);
            message = "Delete the file successfully";
            responseCommon.setData(check);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not delete the file!";
            responseCommon.setData(check);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
}
