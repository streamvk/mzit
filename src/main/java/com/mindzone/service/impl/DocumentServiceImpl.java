package com.mindzone.service.impl;

import com.mindzone.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Override
    public String upload(MultipartFile file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file.getOriginalFilename())) {
            fileOutputStream.write(file.getBytes());
            return file.getOriginalFilename();
        } catch (IOException ex) {
            log.error("Exception to upload the file {}", ex);
            throw new RuntimeException("Fail to upload file.");
        }
    }

    @Override
    public void download(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
                response.setContentLength((int) file.length());
                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(inputStream, response.getOutputStream());

            }
        } catch (IOException ex) {
            log.error("File " + fileName + " Not Found::", ex);
            throw new RuntimeException("File " + fileName + " Not Found");
        }
    }

}
