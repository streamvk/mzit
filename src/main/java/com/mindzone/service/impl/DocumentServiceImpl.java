package com.mindzone.service.impl;

import com.aspose.words.Document;
import com.mindzone.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${file.path}")
    private String filePath;

    @Override
    public String upload(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            Path path = Paths.get(filePath, file.getOriginalFilename());
            Files.write(path, fileBytes);
            return file.getOriginalFilename();
        } catch (IOException ex) {
            log.error("Exception occurred when uploading the file::", ex);
            throw new RuntimeException("Failed to upload the file.");
        }
    }

    @Override
    public void download(String name) {
        try {
            File file = new File(filePath + "/" + name);
            if (file.exists()) {
                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
                response.setContentLength((int) file.length());
                InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
                FileCopyUtils.copy(inputStream, response.getOutputStream());

            }
        } catch (IOException ex) {
            log.error("File " + name + " Not Found::", ex);
            throw new RuntimeException("File " + name + " Not Found");
        }
    }

    @Override
    public void docxToPdf(String fileName) {
        Document doc;
        String fileNameWithOutExtension = fileName.split("\\.")[0];
        try {
            doc = new Document(filePath + "/" + fileName);
            doc.save(fileNameWithOutExtension + ".pdf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
