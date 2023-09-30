package com.mindzone.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindzone.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
           Path path = Paths.get(filePath,file.getOriginalFilename());
            Files.write(path,fileBytes);
            return file.getOriginalFilename();
        } catch (IOException ex) {
            log.error("Exception to upload the file {}", ex);
            throw new RuntimeException("Fail to upload file.");
        }
    }

    @Override
    public void download(String name) {
        try {
            File file = new File(filePath+"/"+ name);
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
    public void docToPdf(String fileName) {
        String  fileNameWithExtension = fileName.split("\\.")[0];
        try (InputStream docxInputStream = Files.newInputStream(Paths.get(filePath+"/"+ fileName)); XWPFDocument document = new XWPFDocument(docxInputStream); OutputStream pdfOutputStream = Files.newOutputStream(Paths.get(filePath+"/"+ fileNameWithExtension+".pdf"))) {
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, pdfOutputStream);
            pdfDocument.open();

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                pdfDocument.add(new Paragraph(paragraph.getText()));
            }
            pdfDocument.close();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }

}
