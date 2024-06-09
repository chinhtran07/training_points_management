package com.tps.controllers;


import com.tps.dto.TableData;
import com.tps.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class APIPdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/create")
    public ResponseEntity<byte[]> createPdf(@RequestBody TableData tableData) throws IOException {
        ByteArrayOutputStream pdfStream = pdfService.createPdf(tableData);
        byte[] pdfBytes = pdfStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "download.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.CREATED);
    }
}
