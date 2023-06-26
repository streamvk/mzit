package com.mindzone.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    String upload(MultipartFile file);
    void download(String fileName);
}
