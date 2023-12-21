package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.entity.FileStorage;
import com.enigma.shopeymart.service.impl.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileStorage uploadFile(@RequestPart(name = "file")MultipartFile file){
        String result = fileStorageService.uploadFile(file);
        return FileStorage.builder()
                .fileName(result)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @GetMapping(value = "/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){
        Resource resource;
        try {
            resource = fileStorageService.downloadFile(fileName);
        }catch (FileNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
