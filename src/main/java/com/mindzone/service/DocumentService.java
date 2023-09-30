package com.mindzone.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DocumentService {

    String upload(MultipartFile file);
    void download(String name);
    void docToPdf(String fileName);
}
